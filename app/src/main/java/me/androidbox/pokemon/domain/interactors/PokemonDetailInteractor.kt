package me.androidbox.pokemon.domain.interactors

import io.reactivex.Single
import me.androidbox.pokemon.domain.models.PokemonModel

interface PokemonDetailInteractor {
    fun getPokemonDetailById(id: Int): Single<PokemonModel>
    fun getPokemonDetailByName(name: String): Single<PokemonModel>
}
