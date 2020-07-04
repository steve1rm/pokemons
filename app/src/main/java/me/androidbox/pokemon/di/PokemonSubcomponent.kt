package me.androidbox.pokemon.di

import dagger.Subcomponent
import me.androidbox.pokemon.di.scopes.ViewScope
import me.androidbox.pokemon.presentation.screens.PokemonListFragment

@ViewScope
@Subcomponent(modules = [PokemonModule::class])
interface PokemonSubcomponent {
    fun inject(fragment: PokemonListFragment)
}
