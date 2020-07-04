package me.androidbox.pokemon.data.requests

import io.reactivex.Single
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonResultsModel
import java.util.concurrent.TimeUnit

class PokemonListInteractorImp(private val pokemonService: PokemonService) : PokemonListInteractor {
    override fun getListOfPokemons(): Single<PokemonResultsModel> {
        return pokemonService.getPokemons()
            .timeout(10, TimeUnit.SECONDS)
    }
}
