package com.example.netflixremake.kotlin.presentation.categories

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.presentation.movieDetails.MovieDetailsActivity
import com.example.netflixremake.kotlin.data.model.Category
import com.example.netflixremake.kotlin.data.repository.CategoriesApiDataSource
import kotlinx.android.synthetic.main.activity_main.*

class CategoriesActivity : AppCompatActivity() {

    private lateinit var categoryAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: CategoriesViewModel = CategoriesViewModel.ViewModelFactory(CategoriesApiDataSource())
                .create(CategoriesViewModel::class.java)

        viewModel.categoriesLiveData.observe(this, Observer {
            it?.let { categories ->
                categoryAdapter = CategoriesAdapter(categories) {
                    val intent = Intent(this@CategoriesActivity, MovieDetailsActivity::class.java)
                    intent.putExtra("id", it.id)
                    startActivity(intent)
                }
                recycler_view_main.adapter = categoryAdapter
                recycler_view_main.layoutManager = LinearLayoutManager(this)
            }
        })
        viewModel.getCategories()
    }


}