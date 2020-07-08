package me.androidbox.pokemon.pages

import me.androidbox.pokemon.screens.PokemonListScreen

object PokemonListPage {

    val pokemonListScreen: PokemonListScreen by lazy {
        PokemonListScreen()
    }

    fun shouldBeVisible(): PokemonListPage = apply {
        pokemonListScreen {
            container {
                isDisplayed()
            }
        }
    }

    fun shouldHaveSize(size: Int): PokemonListPage = apply {
        pokemonListScreen {
            pokomons {
                hasSize(size)
            }
        }
    }

    fun shouldHaveItemAtPosition(position: Int, _name: String): PokemonListPage = apply {
        pokemonListScreen {
            pokomons {
                childAt<PokemonListScreen.Item>(position) {
                    name.hasText(_name)
                    name.isDisplayed()
                }
            }
        }
    }
}