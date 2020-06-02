package com.example.netflixremake.kotlin.presentation.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.data.model.Category
import com.example.netflixremake.kotlin.data.model.Movie
import kotlinx.android.synthetic.main.category_item.view.*

class CategoriesAdapter(private val categories: List<Category>, private val listener: ((Movie) -> Unit)?) : RecyclerView.Adapter<CategoriesAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.CategoryHolder {
        return CategoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoriesAdapter.CategoryHolder, position: Int): Unit = holder.bind(categories[position])

    inner class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) {
            with(itemView) {
                text_view_title.text = category.name
                recycler_view_movie.adapter = MovieAdapter(category.movies) { movie ->

                    listener?.invoke(movie)
                }
            }
            itemView.recycler_view_movie.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
        }
    }
}
