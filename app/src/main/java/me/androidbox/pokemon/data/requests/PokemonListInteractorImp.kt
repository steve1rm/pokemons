package me.androidbox.pokemon.data.requests

import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonListModel

class PokemonListInteractorImp(private val pokemonService: PokemonService) : PokemonListInteractor {

    override suspend fun getListOfPokemons(): PokemonListModel {
        return pokemonService.getPokemons()
    }

    override suspend fun loadMorePokemonsByOffset(offset: Int): PokemonListModel {
        return pokemonService.loadMorePokemons(offset)
    }

    /*
    override fun getListOfPokemons(): Single<PokemonListModel> {
        return pokemonService.getPokemons()
            .timeout(10, TimeUnit.SECONDS)
    }

    override fun loadMorePokemonsByOffset(offset: Int): Single<PokemonListModel> {
        return pokemonService.loadMorePokemons(offset)
            .timeout(10, TimeUnit.SECONDS)
    }
*/
}
