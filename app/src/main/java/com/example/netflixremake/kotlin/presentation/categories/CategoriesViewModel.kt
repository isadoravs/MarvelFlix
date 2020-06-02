package com.example.netflixremake.kotlin.presentation.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.data.CategoriesResult
import com.example.netflixremake.kotlin.data.model.Category
import com.example.netflixremake.kotlin.data.repository.CategoriesRepository

class CategoriesViewModel(private val dataSource: CategoriesRepository) : ViewModel() {
    val categoriesLiveData: MutableLiveData<List<Category>> = MutableLiveData()
    val errorLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getCategories() {
        dataSource.getCategories { result ->
            when (result) {
                is CategoriesResult.Success -> {
                    categoriesLiveData.value = result.categories
                }
                is CategoriesResult.ApiError -> {
                    if (result.statusCode == 401) {
                        errorLiveData.value = R.string.books_error_401
                    } else {
                        errorLiveData.value = R.string.books_error_400_generic
                    }
                }
                is CategoriesResult.ServerError -> {
                    errorLiveData.value = R.string.books_error_500_generic
                }

            }
        }
    }

    class ViewModelFactory(private val dataSource: CategoriesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
                return CategoriesViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
