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

    /* Método chamado no carregamento da primeira página */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CharacterSeries>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }
    /* Método chamado nas páginas subsequentes */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterSeries>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }
    /* Método chamado na última página */
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
                /*
                *  Busca todos os personagens paginados de 10 em 10 itens (número de itens configurado em CharactersViewModel)
                *  Cada personagem possui um objeto Series
                *  O objeto Series possui uma URI que retorna uma lista de séries que o personagem participou
                *  O método zip une o resultado da requisição de busca dos personagens com a busca de suas respectivas Series
                */

                marvelApi.allCharacters(requestedPage * requestedLoadSize)
                        .flatMap { charactersResult -> Observable.fromIterable(charactersResult.data.results) }
                        .flatMap { character ->
                            Observable.zip(
                                    Observable.just(CharacterSeries(character, ArrayList())),
                                    getFirst(character.series.collectionURI),
                                    BiFunction<CharacterSeries, List<Series>, CharacterSeries> { characterSeries, series ->
                                        characterSeries.series.addAll(series)
                                        characterSeries
                                    })
                        }.subscribe(
                                { (character, series) ->
                                    characterSeries.add(CharacterSeries(character, series))
                                    initialCallback?.onResult(characterSeries, null, adjacentPage)
                                    callback?.onResult(characterSeries, adjacentPage)
                                },
                                { t ->
                                    Log.d("NGVL", "Error loading page: $requestedPage", t)
                                }
                        )
        )
    }

    /* Pega o primeiro observable disponível, ou em cache ou da requisição */
    private fun getFirst(seriesURI: String): Observable<List<Series>>{
        return Observable.concat(
                getCache(seriesURI),
                marvelApi.allSeries(seriesURI).map { s -> s.data.results }
                        .doOnNext { series -> seriesURICache[seriesURI] = series }
        ).first(emptyList()).toObservable()
    }

    /* Verifica se a informação está em cache e retorna se tiver */
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