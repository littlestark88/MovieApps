package com.example.movieapps.presentasion.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapps.BuildConfig.POSTER_URL
import com.example.movieapps.R
import com.example.movieapps.databinding.MovieListItemBinding
import com.example.movieapps.domain.model.MovieList

class MovieAdapter(
    private val onClickListener: (MovieList) -> Unit
): PagingDataAdapter<MovieList, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieList>() {
            override fun areItemsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class MovieViewHolder(private val binding: MovieListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieList) {
            with(binding) {
                tvTitle.text = data.title
                tvDescription.text = data.overview
                tvRating.text = data.voteAverage.toString()
                tvReleaseDate.text = data.releaseDate
                Glide
                    .with(itemView)
                    .load(POSTER_URL + data.posterPath)
                    .placeholder(R.drawable.ic_refresh)
                    .into(imgPoster)
                itemView.setOnClickListener {
                    onClickListener(data)
                }
            }
        }

    }
}