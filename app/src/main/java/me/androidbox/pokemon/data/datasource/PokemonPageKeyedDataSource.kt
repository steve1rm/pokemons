package me.androidbox.pokemon.data.datasource

import androidx.paging.PageKeyedDataSource
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonModel
import timber.log.Timber

class PokemonPageKeyedDataSource(private val pokemonListInteractor: PokemonListInteractor)
    : PageKeyedDataSource<String, PokemonModel>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, PokemonModel>) {
        pokemonListInteractor.getListOfPokemons()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { pokemonListModel ->
                    callback.onResult(pokemonListModel.pokemonList, pokemonListModel.before, pokemonListModel.after)},
                onError = { Timber.e(it, it.localizedMessage) }
            )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, PokemonModel>) {
        pokemonListInteractor.getListOfPokemons()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    callback.onResult(it.pokemonList, it.after)
                },
                onError = { Timber.e(it, it.localizedMessage) }
            )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, PokemonModel>): Unit = Unit
}
