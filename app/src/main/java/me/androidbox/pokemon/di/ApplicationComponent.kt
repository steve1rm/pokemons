package me.androidbox.pokemon.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun add(pokemonModule: PokemonModule): PokemonSubcomponent
}
