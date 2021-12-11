package me.androidbox.pokemon.data.requests

import io.reactivex.rxjava3.core.Single
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.entity.PokemonList
import java.util.concurrent.TimeUnit

class PokemonListInteractorImp(private val pokemonService: PokemonService) : PokemonListInteractor {
    override fun getListOfPokemons(): Single<PokemonList> {
        return pokemonService.getPokemons()
            .timeout(10, TimeUnit.SECONDS)
    }

    override fun loadMorePokemonsByOffset(offset: Int): Single<PokemonList> {
        return pokemonService.loadMorePokemons(offset)
            .timeout(10, TimeUnit.SECONDS)
    }
}