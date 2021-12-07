package me.androidbox.domain.entity

data class PokemonEntity(
    val name: String,
    val height: Float,
    val weight: Float,
    val baseExperience: Int,
    val url: String,
    val sprites: SpriteEntity
)