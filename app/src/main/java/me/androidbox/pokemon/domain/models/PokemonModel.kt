package me.androidbox.pokemon.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonModel(
    @field:[Expose SerializedName("results")]
    val resultsModel: List<ResultsModel>)
