package com.example.netflixremake.kotlin.presentation.movieDetails

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.data.CategoriesResult
import com.example.netflixremake.kotlin.data.MovieDetailsResult
import com.example.netflixremake.kotlin.data.model.Category
import com.example.netflixremake.kotlin.data.model.MovieDetail
import com.example.netflixremake.kotlin.data.repository.CategoriesRepository
import com.example.netflixremake.kotlin.data.repository.MovieDetailsRepository
import com.example.netflixremake.kotlin.presentation.categories.CategoriesViewModel

class MovieDetailsViewModel(private val id: Int, private val dataSource: MovieDetailsRepository) : ViewModel() {
    val movieLiveData: MutableLiveData<MovieDetail> = MutableLiveData()
    val errorLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getMovies() {
        dataSource.getMovieDetails(id) { result ->
            when (result) {
                is MovieDetailsResult.Success -> {
                    movieLiveData.value = result.movieDetail
                }
                is MovieDetailsResult.ApiError -> {
                    if (result.statusCode == 401) {
                        errorLiveData.value = R.string.books_error_401
                    } else {
                        errorLiveData.value = R.string.books_error_400_generic
                    }
                }
                is MovieDetailsResult.ServerError -> {
                    errorLiveData.value = R.string.books_error_500_generic
                }
            }
        }
    }

    class ViewModelFactory(private val id: Int, private val dataSource: MovieDetailsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                return MovieDetailsViewModel(id, dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
