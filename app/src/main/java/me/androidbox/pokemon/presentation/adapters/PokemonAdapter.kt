package me.androidbox.pokemon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import me.androidbox.pokemon.databinding.PokemonListItemBinding
import me.androidbox.pokemon.domain.models.PokemonModel
import me.androidbox.pokemon.presentation.viewholders.PokemonViewHolder
import timber.log.Timber
import java.util.concurrent.TimeUnit

class PokemonAdapter : RecyclerView.Adapter<PokemonViewHolder>() {

    private val pokemonList = mutableListOf<PokemonModel>()
    private lateinit var pokemonTapped: (String) -> Unit
    private lateinit var binding: PokemonListItemBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val pokemonViewHolder = PokemonViewHolder(binding.root)

        pokemonViewHolder.itemView.clicks()
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribeBy(
                onNext = {
                    val name = pokemonList[pokemonViewHolder.adapterPosition].name

                    if(::pokemonTapped.isInitialized) {
                        pokemonTapped(name)
                    }
                },
                onError = { Timber.e(it, "Failed to send pokemon request %s", it.localizedMessage) }
            ).addTo(compositeDisposable)

        return pokemonViewHolder
    }

    override fun getItemCount(): Int = pokemonList.count()

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        binding.pokemonModel = pokemonList[position]
    }

    fun populatePokemons(newPokemonList: List<PokemonModel>) {
        val oldPokemonList = pokemonList
        val diffResult = DiffUtil.calculateDiff(PokemonDiffCallback(
            newPokemonList,
            oldPokemonList))

        this.pokemonList.addAll(newPokemonList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setupPokemonTappedListener(action: (String) -> Unit) {
        pokemonTapped = action
    }

    fun clearResources() {
        compositeDisposable.clear()
    }
}

