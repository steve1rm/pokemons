package me.androidbox.pokemon.di.components

import dagger.Subcomponent
import me.androidbox.pokemon.di.modules.PokemonModule
import me.androidbox.pokemon.di.scopes.ViewScope
import me.androidbox.pokemon.presentation.screens.PokemonListFragment

@ViewScope
@Subcomponent(modules = [PokemonModule::class])
interface PokemonSubcomponent {
    fun inject(fragment: PokemonListFragment)
}
