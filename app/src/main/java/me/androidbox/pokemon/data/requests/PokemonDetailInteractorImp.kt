package me.androidbox.pokemon.data.requests

import io.reactivex.Single
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.entity.PokemonEntity
import java.util.concurrent.TimeUnit

class PokemonDetailInteractorImp(private val pokemonService: PokemonService) : PokemonDetailInteractor {

    override fun getPokemonDetailById(id: Int): Single<PokemonEntity> {
        return pokemonService.getPokemonById(id)
            .timeout(10, TimeUnit.SECONDS)
    }

    override fun getPokemonDetailByName(name: String): Single<PokemonEntity> {
        return pokemonService.getPokemonByName(name)
            .timeout(10, TimeUnit.SECONDS)
    }
}