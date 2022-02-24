package me.androidbox.pokemon.di.modules

import dagger.Binds
import dagger.Module
import me.androidbox.pokemon.mapper.PokemonDomainMapper
import me.androidbox.pokemon.mapper.imp.PokemonDomainMapperImp

@Module
interface PokemonListModule {

    @Binds
    fun bindsPokemonDomainMapper(pokemonDomainMapperImp: PokemonDomainMapperImp): PokemonDomainMapper
}