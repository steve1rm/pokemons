package me.androidbox.pokemon.presentation.adapters.models

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxrelay3.PublishRelay
import com.jakewharton.rxrelay3.Relay
import kotlinx.android.synthetic.main.pokemon_list_item.view.*
import me.androidbox.pokemon.R
import timber.log.Timber

@EpoxyModelClass(layout = R.layout.pokemon_list_item)
abstract class EpoxyPokemonModel : EpoxyModelWithHolder<EpoxyPokemonModel.PokemonHolder>() {

    @EpoxyAttribute
    lateinit var pokemonName: CharSequence

    @EpoxyAttribute
    lateinit var pokemonCardView: CardView

    @EpoxyAttribute
    lateinit var pokemonClickedRelay: Relay<String>

    override fun bind(holder: PokemonHolder) {
        holder.pokemonName.text = pokemonName

        holder.pokemonCardView.setOnClickListener {
            Timber.d("Pokemon clicked $pokemonName")
            pokemonClickedRelay.accept(pokemonName.toString())
        }
    }

    inner class PokemonHolder : EpoxyHolder() {
        lateinit var pokemonName: TextView
        lateinit var pokemonCardView: CardView

        override fun bindView(itemView: View) {
            pokemonName = itemView.tvName
            pokemonCardView = itemView.cvPokemonContainer
        }
    }
}
