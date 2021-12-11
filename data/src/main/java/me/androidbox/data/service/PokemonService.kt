package me.androidbox.data.service

import io.reactivex.rxjava3.core.Single
import me.androidbox.data.models.PokemonListModel
import me.androidbox.data.models.PokemonModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET(EndPoints.POKEMON)
    fun getPokemons(): Single<PokemonListModel>

    @GET(EndPoints.POKEMON_BY_ID)
    fun getPokemonById(@Path("id") id: Int): Single<PokemonModel>

    @GET(EndPoints.POKEMON_BY_NAME)
    fun getPokemonByName(@Path("name") name: String): Single<PokemonModel>

    @GET(EndPoints.POKEMON)
    fun loadMorePokemons(@Query("offset") offset: Int): Single<PokemonListModel>
}