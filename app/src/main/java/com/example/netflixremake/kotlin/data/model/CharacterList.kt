package com.example.netflixremake.kotlin.data.model

import java.io.Serializable

data class CharacterList (val collectionURI: String, val items: List<CharacterSummary>): Serializable
