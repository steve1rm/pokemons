package me.androidbox.pokemon.domain.interactors

import io.reactivex.rxjava3.core.Single
import me.androidbox.pokemon.domain.entity.PokemonListEntity

interface PokemonListInteractor {
    fun getListOfPokemons(): Single<PokemonListEntity>
    fun loadMorePokemonsByOffset(offset: Int): Single<PokemonListEntity>
}