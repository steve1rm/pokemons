package me.androidbox.pokemon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.androidbox.domain.interactors.PokemonDetailInteractor
import me.androidbox.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.datasource.PokemonDataSourceFactory
import me.androidbox.pokemon.datasource.PokemonPageKeyedDataSource
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.entity.Pokemon
import me.androidbox.pokemon.entity.PokemonList
import me.androidbox.pokemon.mapper.PokemonDomainMapper
import timber.log.Timber

class PokemonViewModel(
    private val pokemonListInteractor: PokemonListInteractor,
    private val pokemonDetailInteractor: PokemonDetailInteractor,
    private val pokemonSchedulers: PokemonSchedulers,
    private val pokemonDataSourceFactory: PokemonDataSourceFactory,
    private val pokemonDomainMapper: PokemonDomainMapper
) : ViewModel() {

    companion object {
        @JvmField
        val TAG: String = PokemonViewModel::class.java.simpleName
    }

    lateinit var pokemonPagingListLiveData: LiveData<PagedList<Pokemon>>
    private val compositeDisposable = CompositeDisposable()
    private val pokemonDetailLiveData = MutableLiveData<Pokemon>()
    private val pokemonListLiveData = MutableLiveData<PokemonList>()
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
        pokemonPagingListLiveData = LivePagedListBuilder<Int, Pokemon>(pokemonDataSourceFactory, getPagedListConfig())
            .setBoundaryCallback(object : PagedList.BoundaryCallback<Pokemon>() {
                override fun onItemAtEndLoaded(itemAtEnd: Pokemon) {
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
                onSuccess = { pokemonListEntity ->
                    val pokemonList = pokemonDomainMapper.mapPokemonListFromDomain(pokemonListEntity)
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
                onSuccess = { pokemonEntity ->
                    val pokemon = pokemonDomainMapper.mapPokemonFromDomain(pokemonEntity)
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
                onSuccess = { pokemonEntity ->
                    val pokemon = pokemonDomainMapper.mapPokemonFromDomain(pokemonEntity)

                    shouldShowLoading.postValue(false)
                    pokemonDetailLiveData.postValue(pokemon)
                },
                onError = {
                    shouldShowLoading.value = false
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    fun registerPokemonList(): MutableLiveData<PokemonList> = pokemonListLiveData

    fun registerPokemonDetail(): MutableLiveData<Pokemon> = pokemonDetailLiveData

    fun registerShouldShowLoading(): MutableLiveData<Boolean> = shouldShowLoading

    fun registerPokemonClicked(): LiveData<String> = pokemonClickedLiveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}