package me.androidbox.pokemon.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpriteEntity(
    val backDefault: String
) : Parcelable