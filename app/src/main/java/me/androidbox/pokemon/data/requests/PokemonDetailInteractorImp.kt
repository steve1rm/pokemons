package me.androidbox.pokemon.data.requests

import io.reactivex.rxjava3.core.Single
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.entity.Pokemon
import java.util.concurrent.TimeUnit

class PokemonDetailInteractorImp(private val pokemonService: PokemonService) : PokemonDetailInteractor {

    companion object {
        private const val TIMEOUT = 10L
    }

    override fun getPokemonDetailById(id: Int): Single<Pokemon> {
        return pokemonService.getPokemonById(id)
            .timeout(TIMEOUT, TimeUnit.SECONDS)
    }

    override fun getPokemonDetailByName(name: String): Single<Pokemon> {
        return pokemonService.getPokemonByName(name)
            .timeout(TIMEOUT, TimeUnit.SECONDS)
    }
}