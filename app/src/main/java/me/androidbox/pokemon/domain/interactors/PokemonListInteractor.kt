package me.androidbox.pokemon.domain.interactors

import io.reactivex.Single
import me.androidbox.pokemon.domain.models.PokemonListModel

interface PokemonListInteractor {
    fun getListOfPokemons(): Single<PokemonListModel>
}

