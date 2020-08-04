package me.androidbox.pokemon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonListModel
import me.androidbox.pokemon.domain.models.PokemonModel
import timber.log.Timber

class PokemonViewModel(private val pokemonListInteractor: PokemonListInteractor,
                       private val pokemonDetailInteractor: PokemonDetailInteractor)
    : ViewModel() {

    companion object {
        @JvmField
        val TAG: String = PokemonViewModel::class.java.simpleName
    }

    private val pokemonDetailLiveData = MutableLiveData<PokemonModel>()
    private val pokemonListLiveData = MutableLiveData<PokemonListModel>()
    private val shouldShowLoading = MutableLiveData<Boolean>()
    private val errorMessage = MutableLiveData<String>()

    init {
        Timber.d("PokemonViewModel init")
        getPokemonsList()
    }

    fun getPokemonsList() {
        fetchPokemons {
            pokemonListLiveData.value = pokemonListInteractor.getListOfPokemons()
        }
    }

    fun getMorePokemons(offset: Int) {
        fetchPokemons {
            pokemonListLiveData.value = pokemonListInteractor.loadMorePokemonsByOffset(offset)
        }
    }

    fun getPokemonDetailById(id: Int) {
        fetchPokemons {
            pokemonDetailLiveData.value = pokemonDetailInteractor.getPokemonDetailById(id)
        }
    }

    fun getPokemonDetailByName(name: String) {
        fetchPokemons {
            pokemonDetailLiveData.value = pokemonDetailInteractor.getPokemonDetailByName(name)
        }
    }

    fun registerPokemonList(): LiveData<PokemonListModel> = pokemonListLiveData
    fun registerPokemonDetail(): LiveData<PokemonModel> = pokemonDetailLiveData
    fun registerShouldShowLoading(): LiveData<Boolean> = shouldShowLoading
    fun registerErrorMessages(): LiveData<String> = errorMessage

    private fun fetchPokemons(fetchBlock: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                shouldShowLoading.value = true
                pokemonListLiveData.value = pokemonListInteractor.getListOfPokemons()
            }
            catch(error: Exception) {
                Timber.e(TAG, error.localizedMessage)
                errorMessage.value = error.localizedMessage
            }
            finally {
                shouldShowLoading.value = false
            }
        }
    }
}
