package me.androidbox.pokemon.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.androidbox.pokemon.databinding.FragmentPokemonListBinding
import me.androidbox.pokemon.di.ProvideApplicationComponent.getApplicationComponent
import me.androidbox.pokemon.di.modules.PokemonModule
import me.androidbox.pokemon.presentation.adapters.PokemonAdapter
import timber.log.Timber
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    @Inject
    lateinit var pokemonAdapter: PokemonAdapter

    private lateinit var bindings: FragmentPokemonListBinding

    companion object {
        val TAG: String = PokemonListFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent(requireActivity())
            .add(PokemonModule())
            .inject(this@PokemonListFragment)

        Timber.d(TAG, "Injection success")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindings = FragmentPokemonListBinding.inflate(inflater, container, false)

        return bindings.root
    }
}
