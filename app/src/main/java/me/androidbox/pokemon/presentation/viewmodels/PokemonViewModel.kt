package me.androidbox.pokemon.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jakewharton.rxrelay3.PublishRelay
import com.jakewharton.rxrelay3.Relay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import me.androidbox.pokemon.data.datasource.PokemonDataSourceFactory
import me.androidbox.pokemon.data.datasource.PokemonPageKeyedDataSource
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonListModel
import me.androidbox.pokemon.domain.models.PokemonModel
import timber.log.Timber

class PokemonViewModel(private val pokemonListInteractor: PokemonListInteractor,
                       private val pokemonDetailInteractor: PokemonDetailInteractor,
                       private val pokemonSchedulers: PokemonSchedulers,
                       private val pokemonDataSourceFactory: PokemonDataSourceFactory) : ViewModel() {

    companion object {
        @JvmField
        val TAG: String = PokemonViewModel::class.java.simpleName
    }

    lateinit var pokemonPagingListLiveData: LiveData<PagedList<PokemonModel>>
    private val compositeDisposable = CompositeDisposable()
    private val pokemonDetailLiveData = MutableLiveData<PokemonModel>()
    private val pokemonListLiveData = MutableLiveData<PokemonListModel>()
    private val shouldShowLoading = MutableLiveData<Boolean>()
    private val pokemonClickedLiveData = MutableLiveData<String>()

    init {
        Timber.d("PokemonViewModel init")
        startPagingPokemons()
    }

    private fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .build()
    }

    private fun startPagingPokemons() {
        pokemonPagingListLiveData = LivePagedListBuilder<Int, PokemonModel>(pokemonDataSourceFactory, getPagedListConfig())
            .setBoundaryCallback(object : PagedList.BoundaryCallback<PokemonModel>() {
                override fun onItemAtEndLoaded(itemAtEnd: PokemonModel) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    Timber.d("Reached end of feed")
                }
            })
            .build()
    }

    fun observePagingProgress(): LiveData<Boolean> =
        Transformations.switchMap<PokemonPageKeyedDataSource, Boolean>(
            pokemonDataSourceFactory.pokemonDataSourceLiveData,
            PokemonPageKeyedDataSource::shouldShowProgressNetwork)

    fun observeInitialProgress(): LiveData<Boolean> {
        return Transformations.switchMap<PokemonPageKeyedDataSource, Boolean>(
            pokemonDataSourceFactory.pokemonDataSourceLiveData,
            PokemonPageKeyedDataSource::shimmerProgressLiveData)
    }

    fun getPokemonDetailByName(name: String) {
        shouldShowLoading.postValue(true)

        pokemonDetailInteractor.getPokemonDetailByName(name)
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemon ->
                    shouldShowLoading.postValue(false)
                    pokemonDetailLiveData.postValue(pokemon)
                },
                onError = {
                    shouldShowLoading.value = false
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    fun registerPokemonList(): MutableLiveData<PokemonListModel> = pokemonListLiveData

    fun registerPokemonDetail(): MutableLiveData<PokemonModel> = pokemonDetailLiveData

    fun registerShouldShowLoading(): MutableLiveData<Boolean> = shouldShowLoading

    fun registerPokemonClicked(): LiveData<String> = pokemonClickedLiveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
