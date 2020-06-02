package com.example.netflixremake.kotlin.data.model

import com.google.gson.annotations.SerializedName

data class Movie(var id: Int = 0,
                 @SerializedName("cover_url") var coverUrl: String = "",
                 var title: String = "",
                 var desc: String = "",
                 var cast: String = "")
