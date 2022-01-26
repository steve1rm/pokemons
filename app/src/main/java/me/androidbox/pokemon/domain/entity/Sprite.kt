package me.androidbox.pokemon.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sprite(
    val backDefault: String
) : Parcelable
