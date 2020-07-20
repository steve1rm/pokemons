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
import androidx.recyclerview.widget.RecyclerView
import me.androidbox.pokemon.databinding.FragmentPokemonListBinding
import me.androidbox.pokemon.di.ProvideApplicationComponent.getApplicationComponent
import me.androidbox.pokemon.di.modules.PokemonModule
import me.androidbox.pokemon.presentation.adapters.PokemonAdapter
import me.androidbox.pokemon.presentation.utils.EndlessRecyclerViewScrollListener
import me.androidbox.pokemon.presentation.viewmodels.PokemonViewModel
import timber.log.Timber
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    @Inject
    lateinit var pokemonViewModel: PokemonViewModel

    @Inject
    lateinit var pokemonAdapter: PokemonAdapter

    private lateinit var bindings: FragmentPokemonListBinding

    companion object {
        val TAG: String = PokemonListFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent(requireActivity())
            .add(PokemonModule(this@PokemonListFragment))
            .inject(this@PokemonListFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindings = FragmentPokemonListBinding.inflate(inflater, container, false)

        setupAdapter()

        pokemonViewModel.registerPokemonList().observe(viewLifecycleOwner, Observer { pokemonList ->
            pokemonAdapter.populatePokemons(pokemonList.pokemonList)
        })

        pokemonViewModel.registerPokemonDetail().observe(viewLifecycleOwner, Observer { pokemon ->
            val bottomSheet = PokemonDetailBottomSheet()

            bottomSheet.arguments = Bundle().apply {
                putParcelable(PokemonDetailBottomSheet.POKEMON_DETAIL_KEY, pokemon)
            }
            bottomSheet.show(parentFragmentManager, PokemonDetailBottomSheet::class.java.simpleName)
        })

        pokemonViewModel.registerShouldShowLoading().observe(viewLifecycleOwner, Observer { shouldShow ->
            if(shouldShow) {
                bindings.pbLoading.visibility = View.VISIBLE
                bindings.pbLoading.show()
            }
            else {
                bindings.pbLoading.visibility = View.GONE
                bindings.pbLoading.hide()
            }
        })

        return bindings.root
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)

        pokemonAdapter.setupPokemonTappedListener(::onPokemonTapped)
        bindings.rvPokemons.adapter = pokemonAdapter
        bindings.rvPokemons.layoutManager = layoutManager
     
        val endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadMorePokemons(page)
                Timber.d("page $page totalItemsCount $totalItemsCount")
            }
        }
        bindings.rvPokemons.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    private fun loadMorePokemons(page: Int) {
        /** Offset is calculated by multiplying the actual page number by 20 to get the next 'page * 20' of pokemons */
        val offset = page * 20
        pokemonViewModel.getMorePokemons(offset)
    }

    private fun onPokemonTapped(name: String) {
        pokemonViewModel.getPokemonDetailByName(name)
    }

    override fun onDestroyView() {
        pokemonAdapter.clearResources()
        super.onDestroyView()
    }
}
