package com.example.netflixremake.kotlin.data.repository

import MarvelApi
import android.util.Log
import com.example.netflixremake.kotlin.data.model.Character
import io.reactivex.disposables.CompositeDisposable


class SeriesDataSource(
        private val marvelApi: MarvelApi,
        private val compositeDisposable: CompositeDisposable
) {

    private fun createObservable(characterURI: String) {
        compositeDisposable.add(
                marvelApi.allCharactersBySeries(characterURI)
                        .subscribe(
                                { response ->
                                    Log.d("NGVL", "Loading characters $response")
                                },
                                { t ->
                                    Log.d("NGVL", "Error loading characters", t)
                                }
                        )
        );
    }


}