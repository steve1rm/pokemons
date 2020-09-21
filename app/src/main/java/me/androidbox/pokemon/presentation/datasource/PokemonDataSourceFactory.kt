package me.androidbox.pokemon.presentation.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonModel

class PokemonDataSourceFactory(private val pokemonListInteractor: PokemonListInteractor,
                               private val pokemonDetailInteractor: PokemonDetailInteractor)
    : DataSource.Factory<Int, PokemonModel>() {

    val pokemonDataSourceLiveData = MutableLiveData<PokemonPageKeyedDataSource>()

    override fun create(): DataSource<Int, PokemonModel> {
        val dataSource = PokemonPageKeyedDataSource(pokemonListInteractor, pokemonDetailInteractor)

        pokemonDataSourceLiveData.postValue(dataSource)

        return dataSource
    }
}
