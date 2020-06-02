package com.example.netflixremake.kotlin.data.model

import com.google.gson.annotations.SerializedName

data class Category(@SerializedName("title") var name: String = "",
                    @SerializedName("movie") var movies: MutableList<Movie> = arrayListOf())