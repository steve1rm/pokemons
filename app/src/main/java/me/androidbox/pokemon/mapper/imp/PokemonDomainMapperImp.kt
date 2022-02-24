package me.androidbox.pokemon.mapper.imp

import me.androidbox.domain.entity.PokemonEntity
import me.androidbox.domain.entity.PokemonListEntity
import me.androidbox.domain.entity.SpriteEntity
import me.androidbox.pokemon.entity.Pokemon
import me.androidbox.pokemon.entity.PokemonList
import me.androidbox.pokemon.entity.Sprite
import me.androidbox.pokemon.mapper.PokemonDomainMapper
import javax.inject.Inject

class PokemonDomainMapperImp @Inject constructor() : PokemonDomainMapper {

    override fun mapPokemonListFromDomain(pokemonListEntity: PokemonListEntity): PokemonList {
        return PokemonList(
            pokemonList = pokemonListEntity.pokemonList.map {
                mapPokemonFromDomain(it)
            }
        )
    }

    override fun mapPokemonFromDomain(pokemonEntity: PokemonEntity): Pokemon {
        return Pokemon(
            name = pokemonEntity.name,
            height = pokemonEntity.height,
            weight = pokemonEntity.weight,
            baseExperience = pokemonEntity.baseExperience,
            url = pokemonEntity.url,
            sprites = mapSpritesFromDomain(pokemonEntity.sprites)
        )
    }

    override fun mapSpritesFromDomain(spriteEntity: SpriteEntity): Sprite {
        return Sprite(backDefault = spriteEntity.backDefault)
    }

    override fun mapListOfPokemonFromDomain(listOfPokemonEntity: List<PokemonEntity>): List<Pokemon> {
        return listOfPokemonEntity.map {
            Pokemon(
                name = it.name,
                height = it.height,
                weight = it.weight,
                baseExperience = it.baseExperience,
                url = it.url,
                sprites = mapSpritesFromDomain(it.sprites)
            )
        }
    }
}