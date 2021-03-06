package me.androidbox.pokemon.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.androidbox.pokemon.data.requests.PokemonDetailInteractorImp
import me.androidbox.pokemon.data.requests.PokemonListInteractorImp
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.di.ViewModelPokemonProvider
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.di.scopes.ViewScope
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.presentation.adapters.PokemonAdapter
import me.androidbox.pokemon.presentation.screens.PokemonListFragment
import me.androidbox.pokemon.presentation.viewmodels.PokemonViewModel

@Module
class PokemonModule(private val fragment: PokemonListFragment) {

    @ViewScope
    @Provides
    fun providePokemonListInteractor(pokemonService: PokemonService): PokemonListInteractor =
        PokemonListInteractorImp(pokemonService)

    @ViewScope
    @Provides
    fun providePokemonDetailInteractor(pokemonService: PokemonService): PokemonDetailInteractor =
        PokemonDetailInteractorImp(pokemonService)

    @ViewScope
    @Provides
    fun providePokemonAdapter(): PokemonAdapter =
        PokemonAdapter()

    @ViewScope
    @Provides
    fun providePokemonViewModel(
        pokemonListInteractor: PokemonListInteractor,
        pokemonDetailInteractor: PokemonDetailInteractor,
        pokemonSchedulers: PokemonSchedulers
    ): PokemonViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelPokemonProvider {
                PokemonViewModel(
                    pokemonListInteractor, pokemonDetailInteractor, pokemonSchedulers
                )
            }).get(PokemonViewModel::class.java)
    }
}
