package me.androidbox.pokemon.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import me.androidbox.pokemon.domain.models.PokemonModel

class PokemonDiffCallback(
    private val newPokemonModelList: List<PokemonModel>,
    private val oldPokemonModelList: List<PokemonModel>
) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newPokemonModelList[newItemPosition].name == oldPokemonModelList[oldItemPosition].name &&
            newPokemonModelList[newItemPosition].baseExperience == oldPokemonModelList[oldItemPosition].baseExperience &&
            newPokemonModelList[newItemPosition].weight == oldPokemonModelList[oldItemPosition].weight &&
            newPokemonModelList[newItemPosition].height == oldPokemonModelList[oldItemPosition].height
    }

    override fun getOldListSize(): Int = oldPokemonModelList.count()

    override fun getNewListSize(): Int = newPokemonModelList.count()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newPokemonModelList[newItemPosition] == oldPokemonModelList[oldItemPosition]
    }
}