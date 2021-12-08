package me.androidbox.pokemon.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import me.androidbox.domain.interactors.PokemonDetailInteractor
import me.androidbox.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.entity.PokemonEntity

class PokemonDataSourceFactory(
    private val pokemonListInteractor: PokemonListInteractor,
    private val pokemonDetailInteractor: PokemonDetailInteractor,
    private val pokemonSchedulers: PokemonSchedulers
) :
    DataSource.Factory<Int, PokemonEntity>() {

    val pokemonDataSourceLiveData = MutableLiveData<PokemonPageKeyedDataSource>()

    override fun create(): DataSource<Int, PokemonEntity> {
        val dataSource = PokemonPageKeyedDataSource(pokemonListInteractor, pokemonDetailInteractor, pokemonSchedulers)

        pokemonDataSourceLiveData.postValue(dataSource)

        return dataSource
    }
}