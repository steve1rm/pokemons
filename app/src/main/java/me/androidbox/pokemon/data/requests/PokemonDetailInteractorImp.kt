package me.androidbox.pokemon.data.requests

import io.reactivex.rxjava3.core.Single
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.models.PokemonModel
import java.util.concurrent.TimeUnit

class PokemonDetailInteractorImp(private val pokemonService: PokemonService) : PokemonDetailInteractor {

    override fun getPokemonDetailById(id: Int): Single<PokemonModel> {
        return pokemonService.getPokemonById(id)
            .timeout(10, TimeUnit.SECONDS)
    }

    override fun getPokemonDetailByName(name: String): Single<PokemonModel> {
        return pokemonService.getPokemonByName(name)
            .timeout(10, TimeUnit.SECONDS)
    }
}
