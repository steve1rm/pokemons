package me.androidbox.pokemon.presentation.mockdata

import me.androidbox.pokemon.domain.models.PokemonModel
import java.util.*

object MockDataFactory {

    fun createListOfPokemon(count: Int): List<PokemonModel> {
        val pokemonList = mutableListOf<PokemonModel>()

        repeat(count) {
            pokemonList.add(createPokemon())
        }

        return pokemonList.toList()
    }

    fun createPokemon(): PokemonModel {
        return PokemonModel(
            UUID.randomUUID().toString(),
            (10..100).shuffled().first().toFloat(),
            (10..100).shuffled().first().toFloat(),
            (10..100).shuffled().first())
    }
}