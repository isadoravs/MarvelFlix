package com.example.netflixremake.kotlin.data.paging

import MarvelApi
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.netflixremake.kotlin.data.model.Character
import com.example.netflixremake.kotlin.data.model.Series
import com.example.netflixremake.kotlin.data.response.CharacterSeries
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlin.collections.ArrayList


class CharactersDataSource(
        private val marvelApi: MarvelApi,
        private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, CharacterSeries>() {
    private var characterSeries: MutableList<CharacterSeries> = arrayListOf()
    private var seriesURICache: MutableMap<String, List<Series>> = mutableMapOf()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CharacterSeries>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterSeries>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterSeries>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 requestedLoadSize: Int,
                                 initialCallback: LoadInitialCallback<Int, CharacterSeries>?,
                                 callback: LoadCallback<Int, CharacterSeries>?) {


        compositeDisposable.add(
                marvelApi.allCharacters(requestedPage * requestedLoadSize)
                        .flatMap { charactersResult -> Observable.fromIterable(charactersResult.data.results) }
                        .flatMap { character ->
                            Observable.zip(
                                    Observable.just(CharacterSeries(character, ArrayList())),
                                    getFirst(character),
                                    BiFunction<CharacterSeries, List<Series>, CharacterSeries> { characterSeries, series ->
                                        characterSeries.series.addAll(series)
                                        characterSeries
                                    })
                        }.subscribe(
                                { (character, series) ->
                                    characterSeries.add(CharacterSeries(character, series))
                                   // Log.d("NGVL", "Loading page: $requestedPage")
                                    initialCallback?.onResult(characterSeries, null, adjacentPage)
                                    callback?.onResult(characterSeries, adjacentPage)
                                },
                                { t ->
                                    Log.d("NGVL", "Error loading page: $requestedPage", t)
                                }
                        )
        )
    }

    private fun getFirst(character: Character): Observable<List<Series>>{
        return Observable.concat(
                getCache(character.series.collectionURI),
                marvelApi.allSeries(character.series.collectionURI).map { s -> s.data.results }
                        .doOnNext { series -> seriesURICache[character.series.collectionURI] = series }
        ).first(emptyList()).toObservable()
    }

    private fun getCache(seriesURI: String): Observable<List<Series>>? {
        return Observable.fromIterable(seriesURICache.keys)
                .filter { key ->
                    key == seriesURI
                }
                .map { key ->
                    seriesURICache[key]!!
                }
    }
}