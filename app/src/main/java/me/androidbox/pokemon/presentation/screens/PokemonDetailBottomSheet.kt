package me.androidbox.pokemon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.androidbox.pokemon.R
import me.androidbox.pokemon.databinding.BottomSheetLayoutBinding
import me.androidbox.pokemon.domain.models.PokemonModel

class PokemonDetailBottomSheet : BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        val POKEMON_DETAIL_KEY = "pokemonDetailKey"
    }

    lateinit var binding: BottomSheetLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)

        arguments?.let {
            (it.getParcelable(POKEMON_DETAIL_KEY) as? PokemonModel)?.let { pokemon ->
                binding.tvName.text = pokemon.name
                binding.tvHeight.text = getString(R.string.height, pokemon.height)
                binding.tvWeight.text = getString(R.string.weight, pokemon.weight)
                binding.tvBaseExperience.text = getString(R.string.BaseExp, pokemon.baseExperience)
            }
        }

        return binding.root
    }
}
