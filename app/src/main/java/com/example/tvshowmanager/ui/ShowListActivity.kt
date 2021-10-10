package com.example.tvshowmanager.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tvshowmanager.R
import com.example.tvshowmanager.ShowMovieListQuery
import com.example.tvshowmanager.adapter.MovieListAdapter
import com.example.tvshowmanager.databinding.ActivityShowListBinding
import com.example.tvshowmanager.pojo.Movie
import com.example.tvshowmanager.pojo.ViewState
import com.example.tvshowmanager.viewModel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowListActivity : AppCompatActivity() {
    private val movieList = ArrayList<Movie>()
    private val movieViewModel by viewModel<MovieViewModel>()
    private val showListAdapter by lazy { MovieListAdapter(movieList) }
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityShowListBinding>(this, R.layout.activity_show_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding.rvMovies.adapter = showListAdapter
        observeData()
        movieViewModel.fetchShowList()
    }

    private fun observeData() {
        movieViewModel.dataObserve.apply {
            observe({ lifecycle }, {
                when (it) {
                    is ViewState.Success -> {
                        binding.progressIndecator.visibility = View.GONE
                        refinedata(it.data)
                    }
                    is ViewState.NetworkError -> {
                        binding.progressIndecator.visibility = View.GONE
                        Toast.makeText(
                            this@ShowListActivity,
                            R.string.connectivity_message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    is ViewState.Error -> {
                        binding.progressIndecator.visibility = View.GONE
                        Toast.makeText(this@ShowListActivity, it.data, Toast.LENGTH_SHORT).show()
                    }
                    is ViewState.Loading -> {
                        binding.progressIndecator.visibility = View.VISIBLE
                        Log.e("Loading State--", "-------")
                    }
                }
            })
        }
    }

    private fun refinedata(data: List<ShowMovieListQuery.Edge>) {
        movieList.clear()
        data.forEach {
            val node = it.node
            movieList.add(
                Movie(
                    title = node?.title,
                    releaseDate = node?.releaseDate.toString(),
                    seasons = node?.seasons
                )
            )
        }
        showListAdapter.notifyDataSetChanged()
    }

}