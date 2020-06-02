package com.example.netflixremake.kotlin.data.model

import java.io.Serializable

data class Thumbnail(
        val path: String,
        val extension: String
) : Serializable