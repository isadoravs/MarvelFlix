package com.example.netflixremake.kotlin.data.model

import java.io.Serializable

data class Series (
        val id: Int,
        val title: String,
        val description: String,
        val thumbnail: Thumbnail,
        val characters: CharacterList
) : Serializable
