package me.androidbox.pokemon.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
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
    private val shimmerPublishSubject = PublishSubject.create<Boolean>()
    val shimmerProgressObservable: Observable<Boolean>
        get() = shimmerPublishSubject.hide()

    private val shimmerMutableLiveData = MutableLiveData<Boolean>()
    val shimmerProgressLiveData: LiveData<Boolean>
        get() = shimmerMutableLiveData

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PokemonModel>) {
        shimmerMutableLiveData.postValue(true)

        pokemonListInteractor.getListOfPokemons()
            .flattenAsObservable { it.pokemonList }
            .flatMap {
                pokemonDetailInteractor.getPokemonDetailByName(it.name).toObservable()
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
            .flattenAsObservable { it.pokemonList }
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
