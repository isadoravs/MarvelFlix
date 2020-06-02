package com.example.netflixremake.kotlin.data.response

import com.example.netflixremake.kotlin.data.model.Category
import com.google.gson.annotations.SerializedName

data class CategoriesResponse(@SerializedName("category") val categories: List<Category>)
