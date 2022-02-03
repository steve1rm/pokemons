package me.androidbox.pokemon.presentation.mockdata

import me.androidbox.domain.entity.PokemonEntity
import me.androidbox.domain.entity.PokemonListEntity
import me.androidbox.domain.entity.SpriteEntity
import java.util.UUID

object MockDataFactory {

    fun createListOfPokemons(count: Int): List<PokemonEntity> {
        val pokemonList = mutableListOf<PokemonEntity>()

        repeat(count) {
            pokemonList.add(createPokemon())
        }

        return pokemonList.toList()
    }

    fun createPokemonList(count: Int = 10): PokemonListEntity {
        return createListOfPokemons(count)
            .run {
                PokemonListEntity(this)
            }
    }

    fun createPokemon(): PokemonEntity {
        return PokemonEntity(
            UUID.randomUUID().toString(),
            (10..100).shuffled().first().toFloat(),
            (10..100).shuffled().first().toFloat(),
            (10..100).shuffled().first(),
            UUID.randomUUID().toString(),
            SpriteEntity(UUID.randomUUID().toString())
        )
    }
}