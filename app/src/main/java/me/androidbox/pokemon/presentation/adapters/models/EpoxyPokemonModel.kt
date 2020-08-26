package me.androidbox.pokemon.presentation.adapters.models

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
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
    lateinit var spriteUrl: String

    @EpoxyAttribute
    lateinit var pokemonClickedRelay: Relay<String>

    override fun bind(holder: PokemonHolder) {
        holder.pokemonName.text = pokemonName

        Glide.with(holder.context)
            .load(spriteUrl)
            .placeholder(R.drawable.ic_image_place_holder)
            .error(R.drawable.ic_broken_image)
            .fallback(R.drawable.ic_no_image)
            .into(holder.spriteImage)

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
        lateinit var spriteImage: ImageView
        lateinit var context: Context

        override fun bindView(itemView: View) {
            pokemonName = itemView.tvName
            pokemonCardView = itemView.cvPokemonContainer
            spriteImage = itemView.ivSprite
            context = itemView.context
        }
    }
}
