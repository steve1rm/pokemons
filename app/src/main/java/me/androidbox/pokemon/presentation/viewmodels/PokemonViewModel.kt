package me.androidbox.pokemon.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import me.androidbox.pokemon.di.modules.ApplicationModule.PokemonSchedulers
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonListModel
import me.androidbox.pokemon.domain.models.PokemonModel
import timber.log.Timber

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

    init {
        Timber.d("PokemonViewModel init")
        getPokemonsList()
    }

    fun getPokemonsList() {
        pokemonListInteractor.getListOfPokemons()
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemonList ->
                    this.pokemonListLiveData.value = pokemonList
                },
                onError = {
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    fun getMorePokemons(offset: Int) {
        pokemonListInteractor.loadMorePokemonsByOffset(offset)
            .subscribeOn(pokemonSchedulers.background())
            .observeOn(pokemonSchedulers.ui())
            .subscribeBy(
                onSuccess = { pokemonList ->
                    pokemonListLiveData.value = pokemonList
                },
                onError = {
                    Timber.e(TAG, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    fun getPokemonDetailById(id: Int) {
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
    }

    fun getPokemonDetailByName(name: String) {
        pokemonDetailInteractor.getPokemonDetailByName(name)
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
    }

    fun registerPokemonList(): MutableLiveData<PokemonListModel> =
        pokemonListLiveData

    fun registerPokemonDetail(): MutableLiveData<PokemonModel> =
        pokemonDetailLiveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
