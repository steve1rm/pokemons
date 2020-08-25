package me.androidbox.pokemon.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonModel
import timber.log.Timber

class PokemonPageKeyedDataSource(
    private val pokemonListInteractor: PokemonListInteractor,
    private val pokemonDetailInteractor: PokemonDetailInteractor)
    : PageKeyedDataSource<Int, PokemonModel>() {

    val compositeDisposable = CompositeDisposable()
    val shouldShowProgressNetwork = MutableLiveData<Boolean>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PokemonModel>) {
        pokemonListInteractor.getListOfPokemons()
            .toObservable()
            .flatMapIterable { it.pokemonList }
            .flatMap {
                pokemonDetailInteractor.getPokemonDetailByName(it.name).toObservable()
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    callback.onResult(it, null, 0)},
                onError = { Timber.e(it, it.localizedMessage) }
            ).addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
        val nextOffSet = params.key + 20

        shouldShowProgressNetwork.postValue(true)
        pokemonListInteractor.loadMorePokemonsByOffset(nextOffSet)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    shouldShowProgressNetwork.postValue(false)
                    callback.onResult(it.pokemonList, nextOffSet )
                },
                onError = {
                    shouldShowProgressNetwork.postValue(false)
                    Timber.e(it, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>): Unit = Unit
}
