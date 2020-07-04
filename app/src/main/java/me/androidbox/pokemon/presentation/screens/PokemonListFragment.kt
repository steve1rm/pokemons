package me.androidbox.pokemon.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.androidbox.pokemon.databinding.FragmentPokemonListBinding

class PokemonListFragment : Fragment() {

    private lateinit var bindings: FragmentPokemonListBinding

    companion object {
        val TAG: String = PokemonListFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindings = FragmentPokemonListBinding.inflate(inflater, container, false)

        return bindings.root
    }
}
