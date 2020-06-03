package com.example.netflixremake.kotlin.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.data.model.Series
import kotlinx.android.synthetic.main.movie_item.view.*

class SeriesByCharacterAdapter(private val series: ArrayList<Series>, private val listener: ((Series) -> Unit)?) : RecyclerView.Adapter<SeriesByCharacterAdapter.SeriesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesHolder {
        return SeriesHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false), listener)
    }

    override fun getItemCount(): Int = series.size

    override fun onBindViewHolder(holder: SeriesHolder, position: Int): Unit = holder.bind(series[position])


    inner class SeriesHolder(itemView: View, private val onClick: ((Series) -> Unit)?) : RecyclerView.ViewHolder(itemView) {
        fun bind(series: Series) {
            with(itemView) {
                Glide.with(context).load(series.thumbnail.path + "/portrait_medium." + series.thumbnail.extension).placeholder(R.drawable.placaholder_bg).into(image_view_cover)
                image_view_cover.setOnClickListener {
                    onClick?.invoke(series)
                }
            }
        }
    }
}