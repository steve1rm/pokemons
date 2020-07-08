package me.androidbox.pokemon.presentation.mockdata

import me.androidbox.pokemon.domain.models.PokemonListModel
import me.androidbox.pokemon.domain.models.PokemonModel
import java.util.*

object MockDataFactory {

    fun createListOfPokemons(count: Int): List<PokemonModel> {
        val pokemonList = mutableListOf<PokemonModel>()

        repeat(count) {
            pokemonList.add(createPokemon())
        }

        return pokemonList.toList()
    }

    fun createPokemonList(count: Int) : PokemonListModel {
        return createListOfPokemons(10)
            .run {
                PokemonListModel(this)
            }
    }

    fun createPokemon(): PokemonModel {
        return PokemonModel(
            UUID.randomUUID().toString(),
            (10..100).shuffled().first().toFloat(),
            (10..100).shuffled().first().toFloat(),
            (10..100).shuffled().first())
    }
}