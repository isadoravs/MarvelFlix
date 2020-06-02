package com.example.netflixremake.kotlin.data.repository

import com.example.netflixremake.kotlin.data.CategoriesResult

interface CategoriesRepository {
    fun getCategories(categoriesResultCallback: (result: CategoriesResult) -> Unit)
}