package com.example.netflixremake.kotlin.data.response

import com.example.netflixremake.kotlin.data.model.Character
import com.example.netflixremake.kotlin.data.model.Series

data class CharacterSeries (
        val character: Character,
        val series: ArrayList<Series>
)