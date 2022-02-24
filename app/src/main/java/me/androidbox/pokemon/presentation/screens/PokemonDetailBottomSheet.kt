package me.androidbox.pokemon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.androidbox.pokemon.R
import me.androidbox.pokemon.databinding.BottomSheetLayoutBinding
import me.androidbox.pokemon.entity.Pokemon

class PokemonDetailBottomSheet : BottomSheetDialogFragment() {

    companion object {
        @JvmStatic
        val POKEMON_DETAIL_KEY = "pokemonDetailKey"
    }

    lateinit var binding: BottomSheetLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)

        arguments?.let {
            (it.getParcelable(POKEMON_DETAIL_KEY) as? Pokemon)?.let { pokemon: Pokemon ->
                binding.tvName.text = pokemon.name
                binding.tvHeight.text = getString(R.string.height, pokemon.height)
                binding.tvWeight.text = getString(R.string.weight, pokemon.weight)
                binding.tvBaseExperience.text = getString(R.string.BaseExp, pokemon.baseExperience)
            }
        }

        return binding.root
    }
}