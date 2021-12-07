package me.androidbox.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonListModel(
    @field:[Expose SerializedName("results")]
    val pokemonList: List<PokemonModel>
) : Parcelable