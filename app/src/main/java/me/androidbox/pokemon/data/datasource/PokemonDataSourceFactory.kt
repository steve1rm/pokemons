package me.androidbox.pokemon.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import me.androidbox.domain.interactors.PokemonDetailInteractor
import me.androidbox.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.entity.Pokemon
import me.androidbox.pokemon.mappers.imp.PokemonDomainMapper

class PokemonDataSourceFactory(
    private val pokemonListInteractor: PokemonListInteractor,
    private val pokemonDetailInteractor: PokemonDetailInteractor,
    private val pokemonSchedulers: PokemonSchedulers,
    private val pokemonDomainMapper: PokemonDomainMapper
) :
    DataSource.Factory<Int, Pokemon>() {

    val pokemonDataSourceLiveData = MutableLiveData<PokemonPageKeyedDataSource>()

    override fun create(): DataSource<Int, Pokemon> {
        val dataSource = PokemonPageKeyedDataSource(pokemonListInteractor, pokemonDetailInteractor, pokemonSchedulers, pokemonDomainMapper)

        pokemonDataSourceLiveData.postValue(dataSource)

        return dataSource
    }
}
