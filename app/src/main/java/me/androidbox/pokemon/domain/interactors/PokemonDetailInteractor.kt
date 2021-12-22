package me.androidbox.pokemon.domain.interactors

import io.reactivex.rxjava3.core.Single
import me.androidbox.pokemon.domain.entity.Pokemon

interface PokemonDetailInteractor {
    fun getPokemonDetailById(id: Int): Single<Pokemon>
    fun getPokemonDetailByName(name: String): Single<Pokemon>
}