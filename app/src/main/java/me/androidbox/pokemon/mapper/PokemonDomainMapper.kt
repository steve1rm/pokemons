package me.androidbox.pokemon.mapper

import me.androidbox.domain.entity.PokemonEntity
import me.androidbox.domain.entity.PokemonListEntity
import me.androidbox.domain.entity.SpriteEntity
import me.androidbox.pokemon.entity.Pokemon
import me.androidbox.pokemon.entity.PokemonList
import me.androidbox.pokemon.entity.Sprite

interface PokemonDomainMapper {
    fun mapPokemonListFromDomain(pokemonListEntity: PokemonListEntity): PokemonList
    fun mapPokemonFromDomain(pokemonEntity: PokemonEntity): Pokemon
    fun mapSpritesFromDomain(spriteEntity: SpriteEntity): Sprite
    fun mapListOfPokemonFromDomain(listOfPokemonEntity: List<PokemonEntity>): List<Pokemon>
}