package me.androidbox.pokemon.di.modules

import dagger.Binds
import dagger.Module
import me.androidbox.pokemon.mappers.imp.PokemonDomainMapper
import me.androidbox.pokemon.mappers.imp.PokemonDomainMapperImp

@Module
interface PokemonListModule {

    @Binds
    fun bindsPokemonDomainMapper(pokemonDomainMapperImp: PokemonDomainMapperImp): PokemonDomainMapper
}