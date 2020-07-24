package me.androidbox.pokemon.data.service

import me.androidbox.pokemon.domain.models.PokemonListModel
import me.androidbox.pokemon.domain.models.PokemonModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET(EndPoints.POKEMON)
    suspend fun getPokemons(): PokemonListModel

    @GET(EndPoints.POKEMON_BY_ID)
    suspend fun getPokemonById(@Path("id") id: Int): PokemonModel

    @GET(EndPoints.POKEMON_BY_NAME)
    suspend fun getPokemonByName(@Path("name") name: String): PokemonModel

    @GET(EndPoints.POKEMON)
    suspend fun loadMorePokemons(@Query("offset") offset: Int): PokemonListModel
}
