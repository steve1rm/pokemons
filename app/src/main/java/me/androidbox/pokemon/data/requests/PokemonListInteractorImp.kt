package me.androidbox.pokemon.data.requests

import io.reactivex.Single
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonModel
import java.util.concurrent.TimeUnit

class PokemonListInteractorImp(private val pokemonService: PokemonService) : PokemonListInteractor {
    override fun getListOfPokemons(): Single<List<PokemonModel>> {
        return pokemonService.getPokemons()
            .timeout(10, TimeUnit.SECONDS)
    }
}
