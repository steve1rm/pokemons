package me.androidbox.pokemon.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import me.androidbox.domain.interactors.PokemonDetailInteractor
import me.androidbox.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.entity.PokemonEntity
import me.androidbox.pokemon.domain.entity.SpriteEntity
import timber.log.Timber

class PokemonPageKeyedDataSource(
    private val pokemonListInteractor: PokemonListInteractor,
    private val pokemonDetailInteractor: PokemonDetailInteractor,
    private val pokemonSchedulers: PokemonSchedulers
) :
    PageKeyedDataSource<Int, PokemonEntity>() {

    val compositeDisposable = CompositeDisposable()
    val shouldShowProgressNetwork = MutableLiveData<Boolean>()

    private val shimmerMutableLiveData = MutableLiveData<Boolean>()
    val shimmerProgressLiveData: LiveData<Boolean>
        get() = shimmerMutableLiveData

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PokemonEntity>
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
            .map { listOfPokemonEntity ->
                listOfPokemonEntity.map {
                    PokemonEntity(
                        name = it.name,
                        height = it.height,
                        weight = it.weight,
                        baseExperience = it.baseExperience,
                        url = it.url,
                        sprites = SpriteEntity(backDefault = it.sprites.backDefault)
                    )
                }
            }
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = {
                    shimmerMutableLiveData.value = false
                    callback.onResult(it, null, 0)
                },
                onError = {
                    shimmerMutableLiveData.value = false
                    Timber.e(it, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonEntity>) {
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
                onSuccess = {
                    shouldShowProgressNetwork.postValue(false)
           //         callback.onResult(it, nextOffSet)
                },
                onError = {
                    shouldShowProgressNetwork.postValue(false)
                    Timber.e(it, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonEntity>): Unit = Unit
}