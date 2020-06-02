package com.example.netflixremake.kotlin.data

import com.example.netflixremake.kotlin.data.model.MovieDetail

sealed class MovieDetailsResult {
    class Success(val movieDetail: MovieDetail) : MovieDetailsResult()
    class ApiError(val statusCode: Int) : MovieDetailsResult()
    object ServerError : MovieDetailsResult()
}