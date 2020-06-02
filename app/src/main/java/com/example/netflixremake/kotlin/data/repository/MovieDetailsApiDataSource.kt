package com.example.netflixremake.kotlin.data.repository

import com.example.netflixremake.kotlin.data.CategoriesResult
import com.example.netflixremake.kotlin.data.MovieDetailsResult
import com.example.netflixremake.kotlin.data.NetflixAPI
import com.example.netflixremake.kotlin.data.model.MovieDetail
import com.example.netflixremake.kotlin.data.response.CategoriesResponse
import com.example.netflixremake.kotlin.data.response.MovieDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsApiDataSource : MovieDetailsRepository {
    override fun getMovieDetails(id: Int, movieDetailsResultCallback: (result: MovieDetailsResult) -> Unit) {

        NetflixAPI.service
                .getMovieBy(id)
                .enqueue(object : Callback<MovieDetailsResponse> {
                    override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                        movieDetailsResultCallback(MovieDetailsResult.ServerError)
                    }

                    override fun onResponse(call: Call<MovieDetailsResponse>, response: Response<MovieDetailsResponse>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                val movieDetail = MovieDetail(it.id, it.coverUrl, it.title, it.desc, it.cast, it.movieSimilar)
                                movieDetailsResultCallback(MovieDetailsResult.Success(movieDetail))
                            }
                        }
                    }
                })
    }
}

