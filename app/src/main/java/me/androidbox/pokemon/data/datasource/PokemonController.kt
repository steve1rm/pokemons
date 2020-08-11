package me.androidbox.pokemon.data.datasource

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import me.androidbox.pokemon.domain.models.PokemonModel
import me.androidbox.pokemon.presentation.adapters.models.EpoxyPokemonModel_
import java.util.*
import javax.inject.Inject

class PokemonController @Inject constructor()
    : PagedListEpoxyController<PokemonModel>() {

    override fun buildItemModel(currentPosition: Int, item: PokemonModel?): EpoxyModel<*> {
        return if(item == null) {
            EpoxyPokemonModel_().id(currentPosition)
        }
        else {
            EpoxyPokemonModel_()
                .pokemonName(item.name)
                .id(UUID.randomUUID().toString())
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
    }
}
