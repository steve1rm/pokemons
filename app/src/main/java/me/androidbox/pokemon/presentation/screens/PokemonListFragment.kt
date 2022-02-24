package me.androidbox.pokemon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import me.androidbox.pokemon.databinding.FragmentPokemonListBinding
import me.androidbox.pokemon.datasource.PokemonController
import me.androidbox.pokemon.di.ProvideApplicationComponent.getApplicationComponent
import me.androidbox.pokemon.di.modules.PokemonModule
import me.androidbox.pokemon.presentation.adapters.PokemonAdapter
import me.androidbox.pokemon.presentation.viewmodels.PokemonViewModel
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    @Inject
    lateinit var pokemonViewModel: PokemonViewModel

    @Inject
    lateinit var pokemonAdapter: PokemonAdapter

    @Inject
    lateinit var pokemonController: PokemonController

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentPokemonListBinding.inflate(inflater, container, false)

        setupEpoxyAdapter()

        pokemonViewModel.registerPokemonDetail().observe(
            viewLifecycleOwner,
            Observer { pokemon ->
                val bottomSheet = PokemonDetailBottomSheet()

                bottomSheet.arguments = Bundle().apply {
                    putParcelable(PokemonDetailBottomSheet.POKEMON_DETAIL_KEY, pokemon)
                }
                bottomSheet.show(parentFragmentManager, PokemonDetailBottomSheet::class.java.simpleName)
            }
        )

        pokemonController.bindPokemonNameClickedRelay().subscribe { pokemon ->
            pokemonViewModel.getPokemonDetailByName(pokemon)
        }

        pokemonViewModel.registerShouldShowLoading().observe(
            viewLifecycleOwner,
            Observer { shouldShow ->
                if (shouldShow) {
                    bindings.pbLoading.visibility = View.VISIBLE
                    bindings.pbLoading.show()
                } else {
                    bindings.pbLoading.visibility = View.GONE
                    bindings.pbLoading.hide()
                }
            }
        )

        pokemonViewModel.pokemonPagingListLiveData.observe(
            viewLifecycleOwner,
            Observer { pagedList ->
                pokemonController.submitList(pagedList)
            }
        )

        pokemonViewModel.observePagingProgress().observe(
            viewLifecycleOwner,
            Observer { shouldShow ->
                if (shouldShow) {
                    bindings.pbLoading.visibility = View.VISIBLE
                    bindings.pbLoading.show()
                } else {
                    bindings.pbLoading.visibility = View.GONE
                    bindings.pbLoading.hide()
                }
            }
        )

        pokemonViewModel.observeInitialProgress().observe(
            viewLifecycleOwner,
            Observer { shouldShowShimmer ->
                if (shouldShowShimmer) {
                    bindings.shimmerFrameLayout.startShimmer()
                } else {
                    bindings.shimmerFrameLayout.stopShimmer()
                    bindings.shimmerFrameLayout.visibility = View.GONE
                }
            }
        )

        return bindings.root
    }

    private fun setupEpoxyAdapter() {
        with(bindings.rvPokemons) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pokemonController.adapter
        }
    }

    override fun onDestroyView() {
        pokemonAdapter.clearResources()
        super.onDestroyView()
    }
}