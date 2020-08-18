package me.androidbox.pokemon.presentation.adapters.models

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxrelay3.Relay
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.pokemon_list_item.view.*
import me.androidbox.pokemon.R
import me.androidbox.pokemon.presentation.utils.EpoxyBaseModel
import me.androidbox.pokemon.presentation.utils.EpoxyBaseViewHolder
import timber.log.Timber
import java.util.concurrent.TimeUnit

@EpoxyModelClass(layout = R.layout.pokemon_list_item)
abstract class EpoxyPokemonModel : EpoxyBaseModel() {
    @EpoxyAttribute
    lateinit var pokemonName: CharSequence

    @EpoxyAttribute
    lateinit var pokemonClickedRelay: Relay<String>

    override fun bind(holder: EpoxyBaseViewHolder) {
        holder.itemView.apply {
            tvName.text = pokemonName.toString()

            cvPokemonContainer.clicks()
                .debounce(300, TimeUnit.MILLISECONDS)
                .map {
                    pokemonName.toString()
                }
                .subscribeBy(
                    onNext = pokemonClickedRelay::accept,
                    onError = { Timber.e(it.localizedMessage, "Failed to click pokemon") }
                )
        }
    }
}
