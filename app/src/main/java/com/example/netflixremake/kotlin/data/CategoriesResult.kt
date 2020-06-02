package com.example.netflixremake.kotlin.data

import com.example.netflixremake.kotlin.data.model.Category

sealed class CategoriesResult {
    class Success(val categories: List<Category>) : CategoriesResult()
    class ApiError(val statusCode: Int) : CategoriesResult()
    object ServerError : CategoriesResult()
}