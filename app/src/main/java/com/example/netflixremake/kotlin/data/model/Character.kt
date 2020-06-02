package com.example.netflixremake.kotlin.data.model

import java.io.Serializable

class Character (
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val series: CharacterList
) : Serializable