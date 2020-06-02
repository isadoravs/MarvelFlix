package com.example.netflixremake.kotlin.presentation.movieDetails

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.data.repository.MovieDetailsApiDataSource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie.*


class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movieAdapter: SimilarMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            it.title = null
        }

        intent.extras?.let {
            val id = it.getInt("id")
            val viewModel: MovieDetailsViewModel = MovieDetailsViewModel.ViewModelFactory(id, MovieDetailsApiDataSource())
                    .create(MovieDetailsViewModel::class.java)

            viewModel.movieLiveData.observe(this, Observer { movieDetail ->
                movieDetail?.let {
                    movieAdapter = SimilarMoviesAdapter(movieDetail.movieSimilar)
                    recycler_view_similar.adapter = movieAdapter
                    recycler_view_similar.layoutManager = GridLayoutManager(this, 3)

                    text_view_title.text = movieDetail.title
                    text_view_desc.text = movieDetail.desc
                    text_view_cast.text = getString(R.string.cast, movieDetail.cast)

                    Glide.with(this@MovieDetailsActivity)
                            .load(movieDetail.coverUrl)
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

                }
            })

            viewModel.errorLiveData.observe(this, Observer { error ->
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            })

            viewModel.getMovies()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}


