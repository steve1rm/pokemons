package me.androidbox.pokemon.domain.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonModel(
    @field:[Expose SerializedName("name")]
    val name: String,

    @field:[Expose SerializedName("height")]
    val height: Float,

    @field:[Expose SerializedName("weight")]
    val weight: Float,

    @field:[Expose SerializedName("base_experience")]
    val baseExperience: Int
) : Parcelable


