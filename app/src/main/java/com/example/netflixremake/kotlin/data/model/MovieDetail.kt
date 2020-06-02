package com.example.netflixremake.kotlin.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(var id: Int,
                       @SerializedName("cover_url") var coverUrl: String = "",
                       var title: String = "",
                       var desc: String = "",
                       var cast: String = "",
                       @SerializedName("movie") val movieSimilar: List<Movie>)