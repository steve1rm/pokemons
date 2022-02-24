package me.androidbox.pokemon.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import me.androidbox.pokemon.entity.Pokemon

@Parcelize
data class PokemonList(
    val pokemonList: List<Pokemon>
) : Parcelable