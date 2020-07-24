package me.androidbox.pokemon.domain.interactors

import me.androidbox.pokemon.domain.models.PokemonModel

interface PokemonDetailInteractor {
    suspend fun getPokemonDetailById(id: Int): PokemonModel
    suspend fun getPokemonDetailByName(name: String): PokemonModel
}
