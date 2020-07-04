package me.androidbox.pokemon.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PokemonModel(
    @field:[Expose SerializedName("name")]
    val name: String)

