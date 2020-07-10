package me.androidbox.pokemon.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.androidbox.pokemon.databinding.PokemonListItemBinding
import me.androidbox.pokemon.domain.models.PokemonModel
import me.androidbox.pokemon.presentation.viewholders.PokemonViewHolder

class PokemonAdapter : RecyclerView.Adapter<PokemonViewHolder>() {

    private val pokemonList = mutableListOf<PokemonModel>()
    private lateinit var pokemonTapped: (String) -> Unit
    private lateinit var binding: PokemonListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val pokemonViewHolder = PokemonViewHolder(binding.root)

        pokemonViewHolder.itemView.setOnClickListener {
            val name = pokemonList[pokemonViewHolder.adapterPosition].name

            if(::pokemonTapped.isInitialized) {
                pokemonTapped(name)
            }
        }

        return pokemonViewHolder
    }

    override fun getItemCount(): Int = pokemonList.count()

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.name.text = pokemonList[position].name
        binding.tvName.text = pokemonList[position].name
    }

    fun populatePokemons(pokemonList: List<PokemonModel>) {
        this.pokemonList.addAll(pokemonList)
    }

    fun setupPokemonTappedListener(action: (String) -> Unit) {
        pokemonTapped = action
    }
}

