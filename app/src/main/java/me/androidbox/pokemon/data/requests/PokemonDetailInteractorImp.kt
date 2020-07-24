package me.androidbox.pokemon.data.requests

import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.models.PokemonModel

class PokemonDetailInteractorImp(private val pokemonService: PokemonService) : PokemonDetailInteractor {

    override suspend fun getPokemonDetailById(id: Int): PokemonModel {
        return pokemonService.getPokemonById(id)
    }

    override suspend fun getPokemonDetailByName(name: String): PokemonModel {
        return pokemonService.getPokemonByName(name)
    }

    /*   override fun getPokemonDetailById(id: Int): PokemonModel {
        return pokemonService.getPokemonById(id)
            .timeout(10, TimeUnit.SECONDS)
    }

    override fun getPokemonDetailByName(name: String): Single<PokemonModel> {
        return pokemonService.getPokemonByName(name)
            .timeout(10, TimeUnit.SECONDS)
    }*/
}
