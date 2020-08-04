package me.androidbox.pokemon.data.requests

import kotlinx.coroutines.withTimeout
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonListModel

class PokemonListInteractorImp(private val pokemonService: PokemonService) : PokemonListInteractor {

    override suspend fun getListOfPokemons(): PokemonListModel {
        return withTimeout(10_000) {
            pokemonService.getPokemons()
        }
    }

    override suspend fun loadMorePokemonsByOffset(offset: Int): PokemonListModel {
        return pokemonService.loadMorePokemons(offset)
    }
}
