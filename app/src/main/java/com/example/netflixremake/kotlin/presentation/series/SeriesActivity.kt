package com.example.netflixremake.kotlin.presentation.series

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.data.model.Series
import io.reactivex.android.schedulers.AndroidSchedulers
import com.example.netflixremake.kotlin.data.model.Character
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie.*


class SeriesActivity : AppCompatActivity() {

    private val viewModel: SeriesViewModel by lazy {
        ViewModelProviders.of(this).get(SeriesViewModel::class.java)
    }

    private val adapter: CharactersByMovieAdapter by lazy {
        CharactersByMovieAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            it.title = null
        }
        val series = intent.getSerializableExtra("series") as? Series

        series?.let { movieDetail ->
            text_view_title.text = movieDetail.title
            text_view_desc.text = movieDetail.description
            text_view_cast.text = getString(R.string.cast, movieDetail.description)

            recycler_view_similar.adapter = adapter
            recycler_view_similar.layoutManager = GridLayoutManager(this, 3)

            Glide.with(this@SeriesActivity)
                    .load(movieDetail.thumbnail.path + "/landscape_medium." + series.thumbnail.extension)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return true
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            val drawable: LayerDrawable? = ContextCompat.getDrawable(baseContext, R.drawable.shadows) as LayerDrawable?
                            drawable?.let {
                                drawable.setDrawableByLayerId(R.id.cover_drawable, resource)
                                (target as DrawableImageViewTarget).view.setImageDrawable(drawable)
                            }
                            return true
                        }
                    }).into(image_view_cover)
            val characters: MutableList<Character> = arrayListOf()
            viewModel.loadCharacters(movieDetail.characters.collectionURI)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ response ->
                        adapter.characters = response
                    }, {error -> println(error)})
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}