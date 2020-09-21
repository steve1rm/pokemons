package me.androidbox.pokemon.presentation.datasource

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import me.androidbox.pokemon.domain.models.PokemonModel
import timber.log.Timber

class CreateLivePageListBuilderImp(private val pokemonDataSourceFactory: PokemonDataSourceFactory,
                                   private val pageListConfig: PageListConfig) : CreateLivePageListBuilder {

    override fun createLivePageListBuilder(): LiveData<PagedList<PokemonModel>> {
        return LivePagedListBuilder<Int, PokemonModel>(pokemonDataSourceFactory, pageListConfig.getPagedListConfig())
            .setBoundaryCallback(object : PagedList.BoundaryCallback<PokemonModel>() {
                override fun onItemAtEndLoaded(itemAtEnd: PokemonModel) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    Timber.d("Reached end of feed")
                }
            })
            .build()
    }
}