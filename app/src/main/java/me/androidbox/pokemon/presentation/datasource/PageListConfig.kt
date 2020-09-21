package me.androidbox.pokemon.presentation.datasource

import androidx.paging.PagedList

interface PageListConfig {
    fun getPagedListConfig(): PagedList.Config
}
