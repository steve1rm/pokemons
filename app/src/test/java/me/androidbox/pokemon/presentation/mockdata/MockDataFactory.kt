package me.androidbox.pokemon.presentation.mockdata

import me.androidbox.pokemon.domain.entity.PokemonList
import me.androidbox.pokemon.domain.entity.Pokemon
import me.androidbox.pokemon.domain.entity.Sprite
import java.util.*

object MockDataFactory {

    fun createListOfPokemons(count: Int): List<Pokemon> {
        val pokemonList = mutableListOf<Pokemon>()

        repeat(count) {
            pokemonList.add(createPokemon())
        }

        return pokemonList.toList()
    }

    fun createPokemonList(count: Int): PokemonList {
        return createListOfPokemons(10)
            .run {
                PokemonList(this)
            }
    }

    fun createPokemon(): Pokemon {
        return Pokemon(
            UUID.randomUUID().toString(),
            (10..100).shuffled().first().toFloat(),
            (10..100).shuffled().first().toFloat(),
            (10..100).shuffled().first(),
            UUID.randomUUID().toString(),
            Sprite(UUID.randomUUID().toString())
        )
    }
}