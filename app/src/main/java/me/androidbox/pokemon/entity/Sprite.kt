package me.androidbox.pokemon.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sprite(
    val backDefault: String
) : Parcelable