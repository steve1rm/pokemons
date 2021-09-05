package me.androidbox.pokemon.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonModel
import timber.log.Timber

class PokemonPageKeyedDataSource(
    private val pokemonListInteractor: PokemonListInteractor,
    private val pokemonDetailInteractor: PokemonDetailInteractor)
    : PageKeyedDataSource<Int, PokemonModel>() {

    val compositeDisposable = CompositeDisposable()
    val shouldShowProgressNetwork = MutableLiveData<Boolean>()

    private val shimmerMutableLiveData = MutableLiveData<Boolean>()
    val shimmerProgressLiveData: LiveData<Boolean>
        get() = shimmerMutableLiveData

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PokemonModel>) {
        shimmerMutableLiveData.postValue(true)

        pokemonListInteractor.getListOfPokemons()
            .flattenAsObservable { pokemonListModel ->
                pokemonListModel.pokemonList
            }
            .flatMap {
                pokemonDetailInteractor.getPokemonDetailByName(it.name).toObservable()
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(mainThread())
            .subscribeBy(
                onSuccess = { pokemonListModel ->
                    shimmerMutableLiveData.value = false
                    callback.onResult(pokemonListModel, null, 0)},
                onError = {
                    shimmerMutableLiveData.value = false
                    Timber.e(it, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
        val nextOffSet = params.key + 20

        shouldShowProgressNetwork.postValue(true)
        pokemonListInteractor.loadMorePokemonsByOffset(nextOffSet)
            .flattenAsObservable { pokemonListModel ->
                pokemonListModel.pokemonList
            }
            .flatMap {
                pokemonDetailInteractor.getPokemonDetailByName(it.name).toObservable()
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    shouldShowProgressNetwork.postValue(false)
                    callback.onResult(it, nextOffSet)
                },
                onError = {
                    shouldShowProgressNetwork.postValue(false)
                    Timber.e(it, it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>): Unit = Unit
}
