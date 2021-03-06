package com.example.netflixremake.kotlin.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder

import com.example.netflixremake.kotlin.data.paging.CharactersDataSourceFactory

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.example.netflixremake.kotlin.data.response.CharacterSeries
import io.reactivex.Observable

class CharactersViewModel : ViewModel() {

    var characterList: Observable<PagedList<CharacterSeries>>

    /* envia um composite disposable ao CharacterDataSource para poder descartá-lo */
    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 10 //número de itens por página

    private val sourceFactory: CharactersDataSourceFactory

    init {
        sourceFactory = CharactersDataSourceFactory(compositeDisposable, MarvelApi.getService())

        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(true)
                .build()

        characterList = RxPagedListBuilder(sourceFactory, config)
                .setFetchScheduler(Schedulers.io())
                .buildObservable()
                .cache()

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}