package me.androidbox.pokemon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import me.androidbox.pokemon.data.datasource.PokemonDataSourceFactory
import me.androidbox.pokemon.data.datasource.PokemonPageKeyedDataSource
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.entity.PokemonListEntity
import me.androidbox.pokemon.domain.entity.PokemonEntity
import timber.log.Timber

class PokemonViewModel(
    private val pokemonListInteractor: PokemonListInteractor,
    private val pokemonDetailInteractor: PokemonDetailInteractor,
    private val pokemonSchedulers: PokemonSchedulers,
    private val pokemonDataSourceFactory: PokemonDataSourceFactory
) : ViewModel() {

    companion object {
        @JvmField
        val TAG: String = PokemonViewModel::class.java.simpleName
    }

    lateinit var pokemonPagingListLiveData: LiveData<PagedList<PokemonEntity>>
    private val compositeDisposable = CompositeDisposable()
    private val pokemonDetailLiveData = MutableLiveData<PokemonEntity>()
    private val pokemonListLiveData = MutableLiveData<PokemonListEntity>()
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
        pokemonPagingListLiveData = LivePagedListBuilder<Int, PokemonEntity>(pokemonDataSourceFactory, getPagedListConfig())
            .setBoundaryCallback(object : PagedList.BoundaryCallback<PokemonEntity>() {
                override fun onItemAtEndLoaded(itemAtEnd: PokemonEntity) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    Timber.d("Reached end of feed")
                }
            })
            .build()
    }

    fun observePagingProgress(): LiveData<Boolean> =
        Transformations.switchMap<PokemonPageKeyedDataSource, Boolean>(
            pokemonDataSourceFactory.pokemonDataSourceLiveData,
            PokemonPageKeyedDataSource::shouldShowProgressNetwork
        )

    fun observeInitialProgress(): LiveData<Boolean> {
        return Transformations.switchMap<PokemonPageKeyedDataSource, Boolean>(
            pokemonDataSourceFactory.pokemonDataSourceLiveData,
            PokemonPageKeyedDataSource::shimmerProgressLiveData
        )
    }

    fun getMorePokemons(offset: Int) {
        shouldShowLoading.value = true

        pokemonListInteractor.loadMorePokemonsByOffset(offset)
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemonList ->
                    shouldShowLoading.value = false
                    pokemonListLiveData.value = pokemonList
                },
                onError = {
                    shouldShowLoading.value = false
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    fun getPokemonDetailById(id: Int) {
        pokemonDetailInteractor.getPokemonDetailById(id)
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemon ->
                    pokemonDetailLiveData.value = pokemon
                },
                onError = {
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
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

    fun registerPokemonList(): MutableLiveData<PokemonListEntity> = pokemonListLiveData

    fun registerPokemonDetail(): MutableLiveData<PokemonEntity> = pokemonDetailLiveData

    fun registerShouldShowLoading(): MutableLiveData<Boolean> = shouldShowLoading

    fun registerPokemonClicked(): LiveData<String> = pokemonClickedLiveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}