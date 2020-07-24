package me.androidbox.pokemon.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.launch
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonListModel
import me.androidbox.pokemon.domain.models.PokemonModel
import me.androidbox.pokemon.presentation.utils.NetworkConnectivity
import timber.log.Timber
import java.lang.Exception

class PokemonViewModel(private val pokemonListInteractor: PokemonListInteractor,
                       private val pokemonDetailInteractor: PokemonDetailInteractor,
                       private val pokemonSchedulers: PokemonSchedulers) : ViewModel() {

    companion object {
        @JvmField
        val TAG: String = PokemonViewModel::class.java.simpleName
    }

    private val compositeDisposable = CompositeDisposable()
    private val pokemonDetailLiveData = MutableLiveData<PokemonModel>()
    private val pokemonListLiveData = MutableLiveData<PokemonListModel>()
    private val shouldShowLoading = MutableLiveData<Boolean>()

    init {
        Timber.d("PokemonViewModel init")
        getPokemonsList()
    }

    fun getPokemonsList() {
        viewModelScope.launch {
            try {
                shouldShowLoading.value = true
                val pokemonListModel = pokemonListInteractor.getListOfPokemons()
                pokemonListLiveData.value = pokemonListModel
            }
            catch(error: Exception) {
                Timber.e(TAG, error.localizedMessage)
            }
            finally {
                shouldShowLoading.value = false
            }
        }

/*
        shouldShowLoading.value = true

        pokemonListInteractor.getListOfPokemons()
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemonList ->
                    this.pokemonListLiveData.value = pokemonList
                    shouldShowLoading.value = false
                },
                onError = {
                    shouldShowLoading.value = false
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
*/
    }

    fun getMorePokemons(offset: Int) {
        viewModelScope.launch {
            try {
                shouldShowLoading.value = true
                val pokemonListModel = pokemonListInteractor.loadMorePokemonsByOffset(offset)
                pokemonListLiveData.value = pokemonListModel
            }
            catch(error: Exception) {
                Timber.e(TAG, error.localizedMessage)
            }
            finally {
                shouldShowLoading.value = false
            }
        }

/*
        shouldShowLoading.value = true

        pokemonListInteractor.loadMorePokemonsByOffset(offset)
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemonList ->
                    shouldShowLoading.value = false
                    pokemonListLiveData.value = pokemonList
                },
                onError = {
                    shouldShowLoading.value = false
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
*/
    }

    fun getPokemonDetailById(id: Int) {
        viewModelScope.launch {
            try {
                shouldShowLoading.value = true
                val pokemonModel = pokemonDetailInteractor.getPokemonDetailById(id)
                pokemonDetailLiveData.value = pokemonModel
            }
            catch(error: Exception) {
                Timber.e(TAG, error.localizedMessage)
            }
            finally {
                shouldShowLoading.value = false
            }
        }

/*
        pokemonDetailInteractor.getPokemonDetailById(id)
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemon ->
                    pokemonDetailLiveData.value = pokemon
                },
                onError = {
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
*/
    }

    fun getPokemonDetailByName(name: String) {

        viewModelScope.launch {
            try {
                shouldShowLoading.value = true
                val pokemonModel = pokemonDetailInteractor.getPokemonDetailByName(name)
                pokemonDetailLiveData.value = pokemonModel
            }
            catch(error: Exception) {
                Timber.e(TAG, error.localizedMessage)
            }
            finally {
                shouldShowLoading.value = false
            }
        }

/*
        shouldShowLoading.postValue(true)

        pokemonDetailInteractor.getPokemonDetailByName(name)
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemon ->
                    shouldShowLoading.postValue(false)
                    pokemonDetailLiveData.postValue(pokemon)
                },
                onError = {
                    shouldShowLoading.value = false
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
*/
    }

    fun registerPokemonList(): LiveData<PokemonListModel> = pokemonListLiveData

    fun registerPokemonDetail(): LiveData<PokemonModel> = pokemonDetailLiveData

    fun registerShouldShowLoading(): LiveData<Boolean> = shouldShowLoading

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
