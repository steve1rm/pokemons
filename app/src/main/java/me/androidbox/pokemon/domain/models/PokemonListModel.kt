package me.androidbox.pokemon.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonListModel(
    @field:[Expose SerializedName("results")]
    val pokemonList: List<PokemonModel>,
    val after: String?,
    val before: String?)
