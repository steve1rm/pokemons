package me.androidbox.pokemon.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import me.androidbox.domain.interactors.PokemonDetailInteractor
import me.androidbox.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.entity.Pokemon
import me.androidbox.pokemon.mapper.PokemonDomainMapper

class PokemonDataSourceFactory(
    private val pokemonListInteractor: PokemonListInteractor,
    private val pokemonDetailInteractor: PokemonDetailInteractor,
    private val pokemonSchedulers: PokemonSchedulers,
    private val pokemonDomainMapper: PokemonDomainMapper
) :
    DataSource.Factory<Int, Pokemon>() {

    val pokemonDataSourceLiveData = MutableLiveData<PokemonPageKeyedDataSource>()

    override fun create(): DataSource<Int, Pokemon> {
        val dataSource = PokemonPageKeyedDataSource(
            pokemonListInteractor,
            pokemonDetailInteractor,
            pokemonSchedulers,
            pokemonDomainMapper
        )

        pokemonDataSourceLiveData.postValue(dataSource)

        return dataSource
    }
}