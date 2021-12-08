package me.androidbox.pokemon.data.requests

import io.reactivex.rxjava3.core.Single
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.entity.PokemonListEntity
import java.util.concurrent.TimeUnit

class PokemonListInteractorImp(private val pokemonService: PokemonService) : PokemonListInteractor {
    override fun getListOfPokemons(): Single<PokemonListEntity> {
        return pokemonService.getPokemons()
            .timeout(10, TimeUnit.SECONDS)
    }

    override fun loadMorePokemonsByOffset(offset: Int): Single<PokemonListEntity> {
        return pokemonService.loadMorePokemons(offset)
            .timeout(10, TimeUnit.SECONDS)
    }
}