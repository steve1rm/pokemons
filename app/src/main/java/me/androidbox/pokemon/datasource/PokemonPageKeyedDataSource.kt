package me.androidbox.pokemon.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.androidbox.domain.interactors.PokemonDetailInteractor
import me.androidbox.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.entity.Pokemon
import me.androidbox.pokemon.mapper.PokemonDomainMapper
import timber.log.Timber

class PokemonPageKeyedDataSource(
    private val pokemonListInteractor: PokemonListInteractor,
    private val pokemonDetailInteractor: PokemonDetailInteractor,
    private val pokemonSchedulers: PokemonSchedulers,
    private val pokemonDomainMapper: PokemonDomainMapper
) :
    PageKeyedDataSource<Int, Pokemon>() {

    val compositeDisposable = CompositeDisposable()
    val shouldShowProgressNetwork = MutableLiveData<Boolean>()

    private val shimmerMutableLiveData = MutableLiveData<Boolean>()
    val shimmerProgressLiveData: LiveData<Boolean>
        get() = shimmerMutableLiveData

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Pokemon>
    ) {
        shimmerMutableLiveData.postValue(true)

        pokemonListInteractor.getListOfPokemons()
            .flattenAsObservable { pokemonListModel ->
                pokemonListModel.pokemonList
            }
            .flatMap {
                pokemonDetailInteractor.getPokemonDetailByName(it.name).toObservable()
            }
            .toList()
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { listOfPokemonEntity ->
                    val listOfPokemon = pokemonDomainMapper.mapListOfPokemonFromDomain(listOfPokemonEntity)
                    shimmerMutableLiveData.value = false
                    callback.onResult(listOfPokemon, null, 0)
                },
                onError = {
                    shimmerMutableLiveData.value = false
                    Timber.e(it, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Pokemon>) {
        val nextOffSet = params.key + 20

        shouldShowProgressNetwork.postValue(true)
        pokemonListInteractor.loadMorePokemonsByOffset(nextOffSet)
            .flattenAsObservable { pokemonListModel ->
                pokemonListModel.pokemonList
            }
            .flatMap {
                pokemonDetailInteractor.getPokemonDetailByName(it.name).toObservable()
            }
            .toList()
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { listOfPokemonEntity ->
                    println(listOfPokemonEntity.count())
                    val listOfPokemon = pokemonDomainMapper.mapListOfPokemonFromDomain(listOfPokemonEntity)
                    shouldShowProgressNetwork.postValue(false)
                    callback.onResult(listOfPokemon, nextOffSet)
                },
                onError = {
                    shouldShowProgressNetwork.postValue(false)
                    Timber.e(it, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Pokemon>): Unit = Unit
}