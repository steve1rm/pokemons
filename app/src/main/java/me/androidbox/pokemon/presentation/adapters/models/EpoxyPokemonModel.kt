package me.androidbox.pokemon.presentation.adapters.models

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import kotlinx.android.synthetic.main.pokemon_list_item.view.*
import me.androidbox.pokemon.R

@EpoxyModelClass(layout = R.layout.pokemon_list_item)
abstract class EpoxyPokemonModel : EpoxyModelWithHolder<EpoxyPokemonModel.PokemonHolder>() {

    @EpoxyAttribute
    lateinit var pokemonName: CharSequence

    override fun bind(holder: PokemonHolder) {
        holder.pokemonName.text = pokemonName
    }

    inner class PokemonHolder : EpoxyHolder() {
        lateinit var pokemonName: TextView

        override fun bindView(itemView: View) {
            pokemonName = itemView.tvName
        }
    }
}
