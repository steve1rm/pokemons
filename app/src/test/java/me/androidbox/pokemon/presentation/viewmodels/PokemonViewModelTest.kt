package me.androidbox.pokemon.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeast
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import me.androidbox.pokemon.data.datasource.PokemonDataSourceFactory
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonListModel
import me.androidbox.pokemon.presentation.mockdata.MockDataFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokemonViewModelTest {
    private val pokemonListInteractor: PokemonListInteractor = mock()
    private val pokemonDetailInteractor: PokemonDetailInteractor = mock()
    private val pokemonSchedulers: PokemonSchedulers = mock()
    private val testScheduler: TestScheduler = TestScheduler()
    private lateinit var pokemonViewModel: PokemonViewModel
    private lateinit var pokemonDataSourceFactory: PokemonDataSourceFactory

    private val pokemonListObserver: Observer<PokemonListModel> = mock()

    @get:Rule
    val instantTastRunExecutableRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        whenever(pokemonSchedulers.background()).thenReturn(testScheduler)
        whenever(pokemonSchedulers.ui()).thenReturn(testScheduler)
        whenever(pokemonListInteractor.getListOfPokemons()).thenReturn(Single.just(PokemonListModel(emptyList())))
        pokemonDataSourceFactory = PokemonDataSourceFactory(pokemonListInteractor, pokemonDetailInteractor)

        pokemonViewModel = PokemonViewModel(
            pokemonListInteractor,
            pokemonDetailInteractor,
            pokemonSchedulers,
            pokemonDataSourceFactory)

        pokemonViewModel.registerPokemonList().observeForever(pokemonListObserver)
    }

    @Test
    fun `should get a list of pokemon`() {
        // Arrange
        val pokemonList = MockDataFactory.createListOfPokemons(10)
        whenever(pokemonListInteractor.getListOfPokemons())
            .thenReturn(Single.just(PokemonListModel(pokemonList)))

        // Act
        pokemonViewModel.getMorePokemons(20)
        testScheduler.triggerActions()

        // Assert
        verify(pokemonListInteractor, atLeast(1)).getListOfPokemons()
        assertThat(pokemonViewModel.registerPokemonList().value).isEqualTo(PokemonListModel(pokemonList))
    }

    @Test
    fun `should not get a list of pokemon on error`() {
        // Arrange
           whenever(pokemonListInteractor.loadMorePokemonsByOffset(20))
            .thenReturn(Single.error(Exception("Exception happened")))

        // Act
        pokemonViewModel.getMorePokemons(20)
        testScheduler.triggerActions()

        // Assert
        verify(pokemonListInteractor, atLeast(1)).loadMorePokemonsByOffset(20)
        assertThat(pokemonViewModel.registerPokemonList().value).isNull()
    }
}