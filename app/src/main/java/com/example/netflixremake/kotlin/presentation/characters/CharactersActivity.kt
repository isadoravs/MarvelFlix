package com.example.netflixremake.kotlin.presentation.characters

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflixremake.R
import com.example.netflixremake.kotlin.presentation.series.SeriesActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*


class CharactersActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharactersAdapter by lazy {
        CharactersAdapter() {
            val intent = Intent(this@CharactersActivity, SeriesActivity::class.java)
            intent.putExtra("series", it)
            startActivity(intent)
        }
    }

    private var recyclerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val llm = LinearLayoutManager(this)
        recycler_view_main.layoutManager = llm
        recycler_view_main.adapter = adapter
        subscribeToList()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", recycler_view_main.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    private fun subscribeToList() {
        val disposable = viewModel.characterList
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progress_bar_main.visibility = View.VISIBLE }
                .subscribe(
                        { list ->
                            adapter.submitList(list)
                            if (recyclerState != null) {
                                recycler_view_main.layoutManager?.onRestoreInstanceState(recyclerState)
                                recyclerState = null
                            }
                            progress_bar_main.visibility = View.GONE
                        },
                        { e ->
                            Log.e("NGVL", "Error", e)
                        }
                )
    }
}