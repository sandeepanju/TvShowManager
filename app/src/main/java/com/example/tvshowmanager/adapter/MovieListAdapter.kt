package com.example.tvshowmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshowmanager.R
import com.example.tvshowmanager.databinding.ItemMoviesBinding
import com.example.tvshowmanager.pojo.Movie

class MovieListAdapter(private val movieList: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    class ViewHolder(internal val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movies, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            data = movieList[position]
        }
    }

    override fun getItemCount() = movieList.size
}