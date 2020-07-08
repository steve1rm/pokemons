package me.androidbox.pokemon.data.requests

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import me.androidbox.pokemon.data.service.PokemonService
import me.androidbox.pokemon.presentation.mockdata.MockDataFactory
import org.junit.Before
import org.junit.Test

class PokemonListInteractorImpTest {

    private val pokemonService: PokemonService = mock()
    private lateinit var pokemonListInteractorImp: PokemonListInteractorImp

    @Before
    fun setUp() {
        pokemonListInteractorImp = PokemonListInteractorImp(pokemonService)
    }

    @Test
    fun `should get a list of pokemons`() {
        // Arrange
        val pokomonList = MockDataFactory.createPokemonList(10)
        whenever(pokemonService.getPokemons()).thenReturn(Single.just(pokomonList))

        // Act
        val testObserver =
            pokemonListInteractorImp.getListOfPokemons().test()

        // Assert
        testObserver
            .assertComplete()
            .assertValueCount(1)
            .assertValue(pokomonList)
        verify(pokemonService).getPokemons()
    }

    @Test
    fun `should get a list of pokemons with offset`() {
        // Arrange
        val pokomonList = MockDataFactory.createPokemonList(10)
        whenever(pokemonService.loadMorePokemons(20)).thenReturn(Single.just(pokomonList))

        // Act
        val testObserver =
            pokemonListInteractorImp.loadMorePokemonsByOffset(20).test()

        // Assert
        testObserver
            .assertComplete()
            .assertValueCount(1)
            .assertValue(pokomonList)
        verify(pokemonService).loadMorePokemons(20)
    }
}