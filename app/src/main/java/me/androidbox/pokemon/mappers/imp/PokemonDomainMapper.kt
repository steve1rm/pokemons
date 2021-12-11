package me.androidbox.pokemon.mappers.imp

import me.androidbox.domain.entity.PokemonEntity
import me.androidbox.domain.entity.PokemonListEntity
import me.androidbox.domain.entity.SpriteEntity
import me.androidbox.pokemon.domain.entity.Pokemon
import me.androidbox.pokemon.domain.entity.PokemonList
import me.androidbox.pokemon.domain.entity.Sprite

interface PokemonDomainMapper {
    fun mapPokemonListFromDomain(pokemonListEntity: PokemonListEntity): PokemonList
    fun mapPokemonFromDomain(pokemonEntity: PokemonEntity): Pokemon
    fun mapSpritesFromDomain(spriteEntity: SpriteEntity): Sprite
    fun mapListOfPokemonFromDomain(listOfPokemonEntity: List<PokemonEntity>): List<Pokemon>
}