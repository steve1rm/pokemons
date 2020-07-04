package me.androidbox.pokemon.data.service

import io.reactivex.Single
import me.androidbox.pokemon.domain.models.PokemonModel
import me.androidbox.pokemon.domain.models.ResultsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {
    @GET(EndPoints.POKEMON)
    fun getPokemons(): Single<ResultsModel>

    @GET(EndPoints.POKEMON_BY_ID)
    fun getPokemonById(@Query("id") id: Int): Single<PokemonModel>
}
