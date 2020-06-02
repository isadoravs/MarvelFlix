package com.example.netflixremake.kotlin.presentation.movieDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.data.model.Movie
import kotlinx.android.synthetic.main.movie_item_similar.view.*


class SimilarMoviesAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<SimilarMoviesAdapter.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder =
            MovieHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_similar, parent, false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int): Unit = holder.bind(movies[position])

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(movie: Movie){
            with(itemView) {
                Glide.with(context).load(movie.coverUrl).placeholder(R.drawable.placaholder_bg).into(image_view_similar)
            }
        }
    }

}