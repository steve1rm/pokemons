package me.androidbox.pokemon.data.datasource

import androidx.paging.PagedList

class PageListConfigImp : PageListConfig {

    override fun getPageListConfiguration(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .build()
    }
}