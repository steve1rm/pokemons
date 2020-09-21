package me.androidbox.pokemon.data.datasource

import androidx.paging.PagedList
import io.reactivex.Flowable
import me.androidbox.pokemon.domain.models.PokemonModel

fun interface PageListBuilder {
    fun createPageList(): Flowable<PagedList<PokemonModel>>
}
