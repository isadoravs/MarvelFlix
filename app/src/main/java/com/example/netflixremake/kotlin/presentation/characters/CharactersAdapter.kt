package com.example.netflixremake.kotlin.presentation.characters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.data.model.Character
import com.example.netflixremake.kotlin.data.model.Series
import com.example.netflixremake.kotlin.data.response.CharacterSeries
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.category_item.view.*

class CharactersAdapter(val listener: ((Series) -> Unit)?) : PagedListAdapter<CharacterSeries, CharactersAdapter.CharacterHolder>(characterDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        return CharacterHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val character = getItem(position)
        if (character != null) {
            holder.bind(character)
        }
    }

    inner class CharacterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(characterSeries: CharacterSeries) {
            with(itemView) {
                text_view_title.text = characterSeries.character.name
                recycler_view_movie.adapter = SeriesByCharacterAdapter(characterSeries.series) { series ->
                    listener?.invoke(series)
                }
                recycler_view_movie.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            }
        }
    }

    companion object {
        val characterDiff = object: DiffUtil.ItemCallback<CharacterSeries>() {
            override fun areItemsTheSame(old: CharacterSeries, new: CharacterSeries): Boolean {
                return old.character.id == new.character.id
            }

            override fun areContentsTheSame(old: CharacterSeries, new: CharacterSeries): Boolean {
                return old == new
            }

        }
    }
}