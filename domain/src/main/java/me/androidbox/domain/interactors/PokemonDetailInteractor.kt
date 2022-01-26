package me.androidbox.domain.interactors

import io.reactivex.rxjava3.core.Single
import me.androidbox.domain.entity.PokemonEntity

interface PokemonDetailInteractor {
    fun getPokemonDetailById(id: Int): Single<PokemonEntity>
    fun getPokemonDetailByName(name: String): Single<PokemonEntity>
}
