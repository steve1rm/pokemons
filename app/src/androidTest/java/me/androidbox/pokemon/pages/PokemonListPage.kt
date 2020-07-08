package me.androidbox.pokemon.pages

import me.androidbox.pokemon.screens.PokemonListScreen

object PokemonListPage {

    val pokemonListScreen: PokemonListScreen by lazy {
        PokemonListScreen()
    }

    fun shouldBeVisible(): PokemonListPage = apply {
        pokemonListScreen {
            container {
                isVisible()
            }
        }
    }
}