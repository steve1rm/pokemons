package me.androidbox.pokemon.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val name: String,
    val height: Float,
    val weight: Float,
    val baseExperience: Int,
    val url: String,
    val sprites: Sprite
) : Parcelable
