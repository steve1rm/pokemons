package me.androidbox.pokemon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.androidbox.pokemon.R
import me.androidbox.pokemon.domain.models.PokemonModel
import me.androidbox.pokemon.presentation.viewholders.PokemonViewHolder
import timber.log.Timber

class PokemonAdapter : RecyclerView.Adapter<PokemonViewHolder>() {

    private val pokemonList = mutableListOf<PokemonModel>()
    private lateinit var pokemonTapped: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val pokemonViewHolder = PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false))

        pokemonViewHolder.itemView.setOnClickListener {
            val name = pokemonList[pokemonViewHolder.adapterPosition].name

            if(::pokemonTapped.isInitialized) {
                pokemonTapped(name)
            }
        }

        return pokemonViewHolder
    }

    override fun getItemCount(): Int =
        pokemonList.count()

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.name.text = pokemonList[position].name
    }

    fun populatePokemons(pokemonList: List<PokemonModel>) {
        this.pokemonList.addAll(pokemonList)
    }

    fun setupPokemonTappedListener(action: (String) -> Unit) {
        pokemonTapped = action
    }
}

