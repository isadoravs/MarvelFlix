package com.example.netflixremake.kotlin.presentation.series

import MarvelApi
import androidx.lifecycle.ViewModel
import com.example.netflixremake.kotlin.data.model.Character
import io.reactivex.Observable

class SeriesViewModel() : ViewModel() {
    fun loadCharacters(characterURI: String): Observable<List<Character>> {
        return MarvelApi.getService().allCharactersBySeries(characterURI).map { character -> character.data.results }
    }
}