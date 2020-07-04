package me.androidbox.pokemon.domain.interactors

import io.reactivex.Single
import me.androidbox.pokemon.domain.models.ResultsModel

interface PokemomListInteractor {
    fun getListOfPokemons(): Single<ResultsModel>
}

