package me.androidbox.pokemon.data.requests

import io.reactivex.Single
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemomListInteractor
import me.androidbox.pokemon.domain.models.ResultsModel
import java.util.concurrent.TimeUnit

class PokemonListInteractorImp(private val pokemonService: PokemonService) : PokemomListInteractor {
    override fun getListOfPokemons(): Single<ResultsModel> {
        return pokemonService.getPokemons()
            .timeout(10, TimeUnit.SECONDS)
    }
}
