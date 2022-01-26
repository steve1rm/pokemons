package me.androidbox.data.requests

import io.reactivex.rxjava3.core.Single
import me.androidbox.data.service.PokemonService
import me.androidbox.domain.entity.PokemonEntity
import me.androidbox.domain.entity.SpriteEntity
import me.androidbox.domain.interactors.PokemonDetailInteractor
import java.util.concurrent.TimeUnit

class PokemonDetailInteractorImp(private val pokemonService: PokemonService) :
    PokemonDetailInteractor {

    private companion object {
        const val TIMEOUT = 10L
    }

    override fun getPokemonDetailById(id: Int): Single<PokemonEntity> {
        return pokemonService.getPokemonById(id)
            .map { pokemonModel ->
                PokemonEntity(
                    name = pokemonModel.name,
                    height = pokemonModel.height,
                    weight = pokemonModel.weight,
                    baseExperience = pokemonModel.baseExperience,
                    url = pokemonModel.url,
                    sprites = SpriteEntity(pokemonModel.sprites.backDefault)
                )
            }
            .timeout(TIMEOUT, TimeUnit.SECONDS)
    }

    override fun getPokemonDetailByName(name: String): Single<PokemonEntity> {
        return pokemonService.getPokemonByName(name)
            .map { pokemonModel ->
                PokemonEntity(
                    name = pokemonModel.name,
                    height = pokemonModel.height,
                    weight = pokemonModel.weight,
                    baseExperience = pokemonModel.baseExperience,
                    url = pokemonModel.url,
                    sprites = SpriteEntity(pokemonModel.sprites.backDefault)
                )
            }
            .timeout(TIMEOUT, TimeUnit.SECONDS)
    }
}
