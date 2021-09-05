package me.androidbox.pokemon.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import me.androidbox.pokemon.domain.interactors.PokemonDetailInteractor
import me.androidbox.pokemon.domain.interactors.PokemonListInteractor
import me.androidbox.pokemon.domain.models.PokemonModel

class PokemonPagingSource(private val pokemonListInteractor: PokemonListInteractor,
                          private val pokemonDetailInteractor: PokemonDetailInteractor
) :
    RxPagingSource<Int, List<PokemonModel>>() {

    val compositeDisposable = CompositeDisposable()
    val shouldShowProgressNetwork = MutableLiveData<Boolean>()

    private val shimmerMutableLiveData = MutableLiveData<Boolean>()
    val shimmerProgressLiveData: LiveData<Boolean>
        get() = shimmerMutableLiveData



    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, List<PokemonModel>>> {
        val currentPage = params.key ?: 1
        val previousPage = if(currentPage == 1) null else currentPage.minus(1)

        // Network calls
        return pokemonListInteractor.loadMorePokemonsByOffset(currentPage.plus(20))
            .subscribeOn(Schedulers.io())
            .flattenAsObservable { pokemonListModel ->
                pokemonListModel.pokemonList
            }
            .flatMap {
                pokemonDetailInteractor.getPokemonDetailByName(it.name).toObservable()
            }
            .toList()
            .doOnSubscribe {
                val threadName = Thread.currentThread().name
                shimmerMutableLiveData.postValue(true)
            }
            .doOnEvent { _, _ ->
                shimmerMutableLiveData.postValue(false)
            }
            .map { listOfPokemonModel ->
                LoadResult.Page(
                    data = listOf(listOfPokemonModel),
                    prevKey = null,
                    nextKey = 1
                )
            }
    }

/*
    private fun toLoadResult(listOfPokemonModel: List<PokemonModel>, position: Int): LoadResult<Int, List<PokemonModel>> {
        return LoadResult.Page<Int, List<PokemonModel>>(
            data = listOfPokemonModel,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == listOfPokemonModel.count()) null else position + 20
        )
    }
*/

    override fun getRefreshKey(state: PagingState<Int, List<PokemonModel>>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus((1))
        }
    }
}