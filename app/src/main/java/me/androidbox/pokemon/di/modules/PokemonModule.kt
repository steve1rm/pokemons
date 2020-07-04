package me.androidbox.pokemon.di.modules

import dagger.Module
import dagger.Provides
import me.androidbox.pokemon.data.requests.PokemonDetailInteractorImp
import me.androidbox.pokemon.data.requests.PokemonListInteractorImp
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.di.scopes.ViewScope
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.presentation.adapters.PokemonAdapter

@Module
class PokemonModule {

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
    fun providePokemonAdapter(): PokemonAdapter = PokemonAdapter()
}
