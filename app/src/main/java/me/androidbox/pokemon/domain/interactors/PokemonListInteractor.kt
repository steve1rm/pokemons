package me.androidbox.pokemon.domain.interactors

import me.androidbox.pokemon.domain.models.PokemonListModel

interface PokemonListInteractor {
    suspend fun getListOfPokemons(): PokemonListModel
    suspend fun loadMorePokemonsByOffset(offset: Int): PokemonListModel
}


