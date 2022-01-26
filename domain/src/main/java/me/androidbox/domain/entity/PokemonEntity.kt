package me.androidbox.domain.entity

data class PokemonEntity(
    val name: String = "",
    val height: Float = 0.0F,
    val weight: Float = 0.0F,
    val baseExperience: Int = 0,
    val url: String = "",
    val sprites: SpriteEntity = SpriteEntity()
)
