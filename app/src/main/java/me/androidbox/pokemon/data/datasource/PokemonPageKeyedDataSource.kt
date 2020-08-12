package me.androidbox.pokemon.data.datasource

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonModel
import timber.log.Timber

class PokemonPageKeyedDataSource(private val pokemonListInteractor: PokemonListInteractor)
    : PageKeyedDataSource<Int, PokemonModel>() {

    val compositeDisposable = CompositeDisposable()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PokemonModel>) {
        pokemonListInteractor.getListOfPokemons()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { pokemonListModel ->
                    callback.onResult(pokemonListModel.pokemonList, null, 20)},
                onError = { Timber.e(it, it.localizedMessage) }
            ).addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
        pokemonListInteractor.loadMorePokemonsByOffset(params.key + 20)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    callback.onResult(it.pokemonList, it.after)
                },
                onError = { Timber.e(it, it.localizedMessage) }
            ).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>): Unit = Unit
}
