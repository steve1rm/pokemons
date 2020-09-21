package me.androidbox.pokemon.presentation.datasource

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import me.androidbox.pokemon.domain.models.PokemonModel

interface CreateLivePageListBuilder {
    fun createLivePageListBuilder(): LiveData<PagedList<PokemonModel>>
}