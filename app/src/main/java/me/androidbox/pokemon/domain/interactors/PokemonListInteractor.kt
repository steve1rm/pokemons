package me.androidbox.pokemon.domain.interactors

import io.reactivex.rxjava3.core.Single
import me.androidbox.pokemon.domain.entity.PokemonList

interface PokemonListInteractor {
    fun getListOfPokemons(): Single<PokemonList>
    fun loadMorePokemonsByOffset(offset: Int): Single<PokemonList>
}