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
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.pokemon_list_item.view.*
import me.androidbox.pokemon.R
import timber.log.Timber
import java.util.concurrent.TimeUnit

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

        holder.pokemonCardView.clicks()
            .debounce(300, TimeUnit.MILLISECONDS)
            .map {
                pokemonName.toString()
            }
            .subscribeBy(
                onNext = pokemonClickedRelay::accept,
                onError = { Timber.e(it.localizedMessage, "Failed to click pokemon") }
            )
    }

    class PokemonHolder : EpoxyHolder() {
        lateinit var pokemonName: TextView
        lateinit var pokemonCardView: CardView

        override fun bindView(itemView: View) {
            pokemonName = itemView.tvName
            pokemonCardView = itemView.cvPokemonContainer
        }
    }
}
