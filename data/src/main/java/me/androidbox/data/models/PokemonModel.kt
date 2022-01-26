package me.androidbox.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonModel(
    @field:[Expose SerializedName("name")]
    val name: String = "",

    @field:[Expose SerializedName("height")]
    val height: Float = 0.0F,

    @field:[Expose SerializedName("weight")]
    val weight: Float = 0.0F,

    @field:[Expose SerializedName("base_experience")]
    val baseExperience: Int = 0,

    @field:[Expose SerializedName("url")]
    val url: String = "",

    @field:[Expose SerializedName("sprites")]
    val sprites: SpriteModel = SpriteModel()
) : Parcelable