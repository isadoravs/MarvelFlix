package com.example.netflixremake.kotlin.data.repository

import com.example.netflixremake.kotlin.data.MovieDetailsResult

interface MovieDetailsRepository {
    fun getMovieDetails(id: Int, movieDetailsResultCallback: (result: MovieDetailsResult) -> Unit)
}