package me.androidbox.pokemon.data.datasource

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import me.androidbox.pokemon.domain.models.PokemonModel
import me.androidbox.pokemon.presentation.adapters.models.EpoxyPokemonModel_

class PokemonController()
    : PagedListEpoxyController<PokemonModel>() {

    override fun buildItemModel(currentPosition: Int, item: PokemonModel?): EpoxyModel<*> {
        return if(item == null) {
            EpoxyPokemonModel_().id(currentPosition)
        }
        else {
            EpoxyPokemonModel_()
                .pokemonName(item.name)
        }
    }
}