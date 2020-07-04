package me.androidbox.pokemon.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.androidbox.pokemon.domain.models.PokemonModel
import me.androidbox.pokemon.presentation.viewholders.PokemonViewHolder

class PokemonAdapter : RecyclerView.Adapter<PokemonViewHolder>() {

    private val pokemonList = listOf<PokemonModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val pokemonViewHolder = PokemonViewHolder(parent)

        pokemonViewHolder.itemView.setOnClickListener {

        }

        return pokemonViewHolder
    }

    override fun getItemCount(): Int = pokemonList.count()

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.name.text = pokemonList[position].name
    }

    fun populatePokemons(pokemons: List<PokemonModel>) {
        pokemonList.toMutableList().addAll(pokemons)
    }
}

