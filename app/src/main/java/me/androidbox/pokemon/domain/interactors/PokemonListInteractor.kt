package me.androidbox.pokemon.domain.interactors

import io.reactivex.Single
import me.androidbox.pokemon.domain.entity.PokemonListEntity

interface PokemonListInteractor {
    fun getListOfPokemons(): Single<PokemonListEntity>
    fun loadMorePokemonsByOffset(offset: Int): Single<PokemonListEntity>
}