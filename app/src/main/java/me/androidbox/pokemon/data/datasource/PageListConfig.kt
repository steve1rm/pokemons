package me.androidbox.pokemon.data.datasource

import androidx.paging.PagedList

fun interface PageListConfig {
    fun getPageListConfiguration(): PagedList.Config
}