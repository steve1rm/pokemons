package me.androidbox.pokemon.presentation.datasource

import androidx.paging.PagedList

class PageListConfigImp : PageListConfig {

    override fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .build()
    }
}