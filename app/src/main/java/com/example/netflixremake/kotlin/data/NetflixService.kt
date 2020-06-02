package com.example.netflixremake.kotlin.data

import com.example.netflixremake.kotlin.data.response.CategoriesResponse
import com.example.netflixremake.kotlin.data.response.MovieDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetflixService {
    @GET("home")
    fun listCategories(): Call<CategoriesResponse>

    @GET("{id}")
    fun getMovieBy(@Path("id") id: Int): Call<MovieDetailsResponse>
}