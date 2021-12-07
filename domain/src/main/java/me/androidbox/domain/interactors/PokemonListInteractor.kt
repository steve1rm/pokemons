package me.androidbox.domain.interactors

import io.reactivex.Single
import me.androidbox.domain.entity.PokemonListEntity

interface PokemonListInteractor {
    fun getListOfPokemons(): Single<PokemonListEntity>
    fun loadMorePokemonsByOffset(offset: Int): Single<PokemonListEntity>
}