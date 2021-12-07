package me.androidbox.pokemon.data.service

import io.reactivex.Single
import me.androidbox.pokemon.domain.entity.PokemonListEntity
import me.androidbox.pokemon.domain.entity.PokemonEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET(EndPoints.POKEMON)
    fun getPokemons(): Single<PokemonListEntity>

    @GET(EndPoints.POKEMON_BY_ID)
    fun getPokemonById(@Path("id") id: Int): Single<PokemonEntity>

    @GET(EndPoints.POKEMON_BY_NAME)
    fun getPokemonByName(@Path("name") name: String): Single<PokemonEntity>

    @GET(EndPoints.POKEMON)
    fun loadMorePokemons(@Query("offset") offset: Int): Single<PokemonListEntity>
}