package me.androidbox.pokemon.di.components

import dagger.Component
import me.androidbox.pokemon.di.components.PokemonSubcomponent
import me.androidbox.pokemon.di.modules.ApplicationModule
import me.androidbox.pokemon.di.modules.NetworkModule
import me.androidbox.pokemon.di.modules.PokemonModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun add(pokemonModule: PokemonModule): PokemonSubcomponent
}
