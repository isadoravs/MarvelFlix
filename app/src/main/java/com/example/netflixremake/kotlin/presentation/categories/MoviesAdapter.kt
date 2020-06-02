package com.example.netflixremake.kotlin.presentation.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.data.model.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(private val movies: List<Movie>, private val listener: ((Movie) -> Unit)?) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieHolder {
        return MovieHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false), listener)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int): Unit = holder.bind(movies[position])


    inner class MovieHolder(itemView: View, private val onClick: ((Movie) -> Unit)?) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                Glide.with(context).load(movie.coverUrl).placeholder(R.drawable.placaholder_bg).into(image_view_cover)
                image_view_cover.setOnClickListener {
                    onClick?.invoke(movie)
                }
            }
        }
    }

}