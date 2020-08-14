package me.androidbox.pokemon.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonModel

class PokemonDataSourceFactory(private val pokemonListInteractor: PokemonListInteractor)
    : DataSource.Factory<Int, PokemonModel>() {

    val pokemonDataSourceLiveData = MutableLiveData<PokemonPageKeyedDataSource>()

    override fun create(): DataSource<Int, PokemonModel> {
        val dataSource = PokemonPageKeyedDataSource(pokemonListInteractor)

        pokemonDataSourceLiveData.postValue(dataSource)

        return dataSource
    }
}
