package me.androidbox.pokemon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import me.androidbox.pokemon.databinding.FragmentPokemonListBinding
import me.androidbox.pokemon.di.ProvideApplicationComponent.getApplicationComponent
import me.androidbox.pokemon.di.modules.PokemonModule
import me.androidbox.pokemon.presentation.adapters.PokemonAdapter
import me.androidbox.pokemon.presentation.viewmodels.PokemonViewModel
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    @Inject
    lateinit var pokemonAdapter: PokemonAdapter

    @Inject
    lateinit var pokemonViewModel: PokemonViewModel

    private lateinit var bindings: FragmentPokemonListBinding

    companion object {
        val TAG: String = PokemonListFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent(requireActivity())
            .add(PokemonModule())
            .inject(this@PokemonListFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindings = FragmentPokemonListBinding.inflate(inflater, container, false)

        setupAdapter()

        pokemonViewModel.registerPokemonList().observe(viewLifecycleOwner, Observer { pokemonList ->
            pokemonAdapter.populatePokemons(pokemonList)
        })
        pokemonViewModel.getPokemonsList()

        return bindings.root
    }

    private fun setupAdapter() {
        bindings.rvPokemons.adapter = pokemonAdapter
        bindings.rvPokemons.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        bindings.rvPokemons.addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
    }
}
