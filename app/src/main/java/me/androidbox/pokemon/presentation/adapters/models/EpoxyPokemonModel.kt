package me.androidbox.pokemon.presentation.adapters.models

import android.annotation.SuppressLint
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxrelay3.Relay
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.androidbox.pokemon.R
import me.androidbox.pokemon.databinding.PokemonListItemBinding
import me.androidbox.pokemon.presentation.utils.ViewBindingEpoxyModelWithHolder
import timber.log.Timber
import java.util.concurrent.TimeUnit

@EpoxyModelClass
abstract class EpoxyPokemonModel : ViewBindingEpoxyModelWithHolder<PokemonListItemBinding>() {

    @EpoxyAttribute
    lateinit var pokemonName: CharSequence

    @EpoxyAttribute
    lateinit var spriteUrl: String

    @EpoxyAttribute
    lateinit var pokemonClickedRelay: Relay<String>

    @SuppressLint("CheckResult")
    @Suppress("MagicNumber")
    override fun PokemonListItemBinding.bind() {
        this.tvName.text = pokemonName

        Glide.with(this.root.context)
            .load(spriteUrl)
            .placeholder(R.drawable.ic_image_place_holder)
            .error(R.drawable.ic_broken_image)
            .fallback(R.drawable.ic_no_image)
            .into(this.ivSprite)

        this.cvPokemonContainer.clicks()
            .debounce(250, TimeUnit.MILLISECONDS)
            .map {
                pokemonName.toString()
            }
            .subscribeBy(
                onError = Timber::e,
                onNext = { pokemonName ->
                    pokemonClickedRelay.accept(pokemonName)
                }
            )
    }

    override fun getDefaultLayout(): Int {
        return R.layout.pokemon_list_item
    }
}