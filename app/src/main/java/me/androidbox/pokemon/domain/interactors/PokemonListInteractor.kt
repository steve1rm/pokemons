package me.androidbox.pokemon.domain.interactors

import io.reactivex.rxjava3.core.Single
import me.androidbox.pokemon.domain.models.PokemonListModel

interface PokemonListInteractor {
    fun getListOfPokemons(): Single<PokemonListModel>
    fun loadMorePokemonsByOffset(offset: Int): Single<PokemonListModel>
}


