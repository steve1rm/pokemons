package me.androidbox.pokemon.presentation.mockdata

import me.androidbox.domain.entity.PokemonEntity
import me.androidbox.domain.entity.PokemonListEntity
import me.androidbox.domain.entity.SpriteEntity
import me.androidbox.pokemon.domain.entity.PokemonList
import me.androidbox.pokemon.domain.entity.Pokemon
import me.androidbox.pokemon.domain.entity.Sprite
import java.util.*

object MockDataFactory {

    fun createListOfPokemons(count: Int): List<PokemonEntity> {
        val pokemonList = mutableListOf<PokemonEntity>()

        repeat(count) {
            pokemonList.add(createPokemon())
        }

        return pokemonList.toList()
    }

    fun createPokemonList(count: Int): PokemonListEntity {
        return createListOfPokemons(10)
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