package com.example.netflixremake.kotlin.data.paging

import MarvelApi
import androidx.paging.DataSource
import com.example.netflixremake.kotlin.data.response.CharacterSeries
import io.reactivex.disposables.CompositeDisposable



class CharactersDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val marvelApi: MarvelApi
) : DataSource.Factory<Int, CharacterSeries>() {

    override fun create(): DataSource<Int, CharacterSeries> {
        return CharactersDataSource(marvelApi, compositeDisposable)
    }
}