package me.androidbox.pokemon.data.datasource

import androidx.paging.DataSource
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonModel

class PokemonDataSourceFactory(private val pokemonListInteractor: PokemonListInteractor)
    : DataSource.Factory<String, PokemonModel>() {

    override fun create(): DataSource<String, PokemonModel> {
        return PokemonPageKeyedDataSource(pokemonListInteractor)
    }
}
