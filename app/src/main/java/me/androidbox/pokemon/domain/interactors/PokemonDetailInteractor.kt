package me.androidbox.pokemon.domain.interactors

import io.reactivex.Single
import me.androidbox.pokemon.domain.entity.PokemonEntity

interface PokemonDetailInteractor {
    fun getPokemonDetailById(id: Int): Single<PokemonEntity>
    fun getPokemonDetailByName(name: String): Single<PokemonEntity>
}