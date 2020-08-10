package me.androidbox.pokemon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import me.androidbox.pokemon.data.datasource.PokemonDataSourceFactory
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonListModel
import me.androidbox.pokemon.domain.models.PokemonModel
import me.androidbox.pokemon.presentation.utils.NetworkConnectivity
import timber.log.Timber

class PokemonViewModel(private val pokemonListInteractor: PokemonListInteractor,
                       private val pokemonDetailInteractor: PokemonDetailInteractor,
                       private val pokemonSchedulers: PokemonSchedulers,
                       private val pokemonDataSourceFactory: PokemonDataSourceFactory) : ViewModel() {

    companion object {
        @JvmField
        val TAG: String = PokemonViewModel::class.java.simpleName
    }

    lateinit var feed: LiveData<PagedList<PokemonModel>>
    private val compositeDisposable = CompositeDisposable()
    private val pokemonDetailLiveData = MutableLiveData<PokemonModel>()
    private val pokemonListLiveData = MutableLiveData<PokemonListModel>()
    private val shouldShowLoading = MutableLiveData<Boolean>()

    init {
        Timber.d("PokemonViewModel init")
      //  getPokemonsList()
        startPagingPokemons()
    }

    fun startPagingPokemons() {
        feed = LivePagedListBuilder<Int, PokemonModel>(pokemonDataSourceFactory, getPagedListConfig())
            .setBoundaryCallback(object : PagedList.BoundaryCallback<PokemonModel>() {
                override fun onItemAtEndLoaded(itemAtEnd: PokemonModel) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    Timber.d("Reached end of feed")
                }
            })
            .build()
    }

    fun getPokemonsList() {
        shouldShowLoading.value = true

        pokemonListInteractor.getListOfPokemons()
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemonList ->
                    this.pokemonListLiveData.value = pokemonList
                    shouldShowLoading.value = false
                },
                onError = {
                    shouldShowLoading.value = false
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
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

    fun registerPokemonList(): MutableLiveData<PokemonListModel> = pokemonListLiveData

    fun registerPokemonDetail(): MutableLiveData<PokemonModel> = pokemonDetailLiveData

    fun registerShouldShowLoading(): MutableLiveData<Boolean> = shouldShowLoading

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(20)
            .build()
    }
}
