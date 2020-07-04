package me.androidbox.pokemon.domain.interactors

import io.reactivex.Single
import me.androidbox.pokemon.domain.models.PokemonModel

interface PokemonListInteractor {
    fun getListOfPokemons(): Single<List<PokemonModel>>
}

