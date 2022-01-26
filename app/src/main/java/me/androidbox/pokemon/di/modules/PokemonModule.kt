package me.androidbox.pokemon.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.androidbox.data.requests.PokemonDetailInteractorImp
import me.androidbox.data.requests.PokemonListInteractorImp
import me.androidbox.data.service.PokemonService
import me.androidbox.domain.interactors.PokemonDetailInteractor
import me.androidbox.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.data.datasource.PokemonDataSourceFactory
import me.androidbox.pokemon.di.ViewModelPokemonProvider
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.di.scopes.ViewScope
import me.androidbox.pokemon.mappers.imp.PokemonDomainMapper
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
    fun providePokemonDataSourceFactory(
        pokemonListInteractor: PokemonListInteractor,
        pokemonDetailInteractor: PokemonDetailInteractor,
        pokemonSchedulers: PokemonSchedulers,
        pokemonDomainMapper: PokemonDomainMapper
    ): PokemonDataSourceFactory {
        return PokemonDataSourceFactory(
            pokemonListInteractor, pokemonDetailInteractor, pokemonSchedulers,
            pokemonDomainMapper
        )
    }

    @ViewScope
    @Provides
    fun providePokemonViewModel(
        pokemonListInteractor: PokemonListInteractor,
        pokemonDetailInteractor: PokemonDetailInteractor,
        pokemonSchedulers: PokemonSchedulers,
        pokemonDatasourceFactory: PokemonDataSourceFactory,
        pokemonDomainMapper: PokemonDomainMapper
    ): PokemonViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelPokemonProvider {
                PokemonViewModel(
                    pokemonListInteractor,
                    pokemonDetailInteractor,
                    pokemonSchedulers,
                    pokemonDatasourceFactory,
                    pokemonDomainMapper
                )
            }
        ).get(PokemonViewModel::class.java)
    }
}