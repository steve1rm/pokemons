package me.androidbox.pokemon.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonListModel
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
    private val pokemonListObserver: Observer<PokemonListModel> = mock()

    @get:Rule
    val instantTastRunExecutableRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        whenever(pokemonSchedulers.background()).thenReturn(testScheduler)
        whenever(pokemonSchedulers.ui()).thenReturn(testScheduler)

        pokemonViewModel = PokemonViewModel(
            pokemonListInteractor,
            pokemonDetailInteractor,
            pokemonSchedulers)
        pokemonViewModel.registerPokemonList().observeForever(pokemonListObserver)
    }

    @Test
    fun `should get a list of pokemon`() {
        // Arrange
        whenever(pokemonListInteractor.getListOfPokemons())
            .thenReturn(Single.just(PokemonListModel(emptyList())))

        // Act
        pokemonViewModel.getPokemonsList()
        testScheduler.triggerActions()

        // Assert
        verify(pokemonListInteractor).getListOfPokemons()
        assertThat(pokemonViewModel.registerPokemonList().value).isEqualTo(PokemonListModel(emptyList()))
    }
}