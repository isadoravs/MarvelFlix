package com.example.netflixremake.kotlin.data.response

import com.example.netflixremake.kotlin.data.model.Movie
import com.example.netflixremake.kotlin.data.model.MovieDetail
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(var id: Int,
                                @SerializedName("cover_url") var coverUrl: String = "",
                                var title: String = "",
                                var desc: String = "",
                                var cast: String = "",
                                @SerializedName("movie") val movieSimilar: List<Movie>
) {
    fun getMovieDetailModel() = MovieDetail(
            id = this.id,
            coverUrl = this.coverUrl,
            title = this.title,
            desc = this.desc,
            cast = this.cast,
            movieSimilar = this.movieSimilar)
}