package me.androidbox.data.requests

import io.reactivex.rxjava3.core.Single
import me.androidbox.data.service.PokemonService
import me.androidbox.domain.entity.PokemonEntity
import me.androidbox.domain.entity.PokemonListEntity
import me.androidbox.domain.entity.SpriteEntity
import me.androidbox.domain.interactors.PokemonListInteractor
import java.util.concurrent.TimeUnit

class PokemonListInteractorImp(private val pokemonService: PokemonService) : PokemonListInteractor {
    override fun getListOfPokemons(): Single<PokemonListEntity> {
        val result = pokemonService.getPokemons()
            .map { pokemonListModel ->
                PokemonListEntity(
                    pokemonList = pokemonListModel.pokemonList.map {
                        PokemonEntity(
                            name = it.name,
                            url = it.url
                        )
                    }
                )
            }

        return result
    }

    override fun loadMorePokemonsByOffset(offset: Int): Single<PokemonListEntity> {
        return pokemonService.loadMorePokemons(offset)
            .map { pokemonListModel ->
                PokemonListEntity(
                    pokemonList = pokemonListModel.pokemonList.map { pokemonModel ->
                        PokemonEntity(
                            name = pokemonModel.name,
                            height = pokemonModel.height,
                            weight = pokemonModel.weight,
                            baseExperience = pokemonModel.baseExperience,
                            url = pokemonModel.url,
                            sprites = SpriteEntity(pokemonModel.sprites.backDefault)
                        )
                    }
                )
            }
            .timeout(10, TimeUnit.SECONDS)
    }
}