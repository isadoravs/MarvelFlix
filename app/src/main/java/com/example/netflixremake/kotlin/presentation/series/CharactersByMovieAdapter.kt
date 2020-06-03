package com.example.netflixremake.kotlin.presentation.series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netflixremake.R
import kotlinx.android.synthetic.main.movie_item_similar.view.*
import com.example.netflixremake.kotlin.data.model.Character

class CharactersByMovieAdapter() : RecyclerView.Adapter<CharactersByMovieAdapter.CharactersHolder>() {
    var characters: List<Character> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersHolder {
        return CharactersHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_similar, parent, false))
    }

    override fun getItemCount(): Int =
            characters.count()


    override fun onBindViewHolder(holder: CharactersHolder, position: Int): Unit = holder.bind(characters[position])

    inner class CharactersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Character) {
            with(itemView) {
                character.let {
                    Glide.with(context).load(it.thumbnail.path + "/portrait_medium." + it.thumbnail.extension).placeholder(R.drawable.placaholder_bg).into(image_view_similar)
                }
            }
        }
    }

}