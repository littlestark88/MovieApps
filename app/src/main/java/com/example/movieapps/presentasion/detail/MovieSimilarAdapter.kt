package com.example.movieapps.presentasion.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapps.BuildConfig.POSTER_URL
import com.example.movieapps.R
import com.example.movieapps.databinding.MovieSimilarListItemBinding
import com.example.movieapps.domain.model.MovieList

class MovieSimilarAdapter(
    private val onClickListener: (MovieList) -> Unit
): PagingDataAdapter<MovieList, MovieSimilarAdapter.MovieSimilarViewHolder>(SIMILAR_DIFF_CALLBACK) {

    companion object {
        val SIMILAR_DIFF_CALLBACK = object: DiffUtil.ItemCallback<MovieList>() {
            override fun areItemsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: MovieSimilarViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSimilarViewHolder {
        val binding = MovieSimilarListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieSimilarViewHolder(binding)
    }

    inner class MovieSimilarViewHolder(private val binding: MovieSimilarListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieList) {
            with(binding) {
                tvTitle.text = data.title
                Glide
                    .with(itemView)
                    .load(POSTER_URL + data.posterPath)
                    .placeholder(R.drawable.ic_refresh)
                    .into(imgPoster)
                itemView.setOnClickListener {
                    onClickListener(data)
                }
                Log.e("movieSimilarAdapter", "bind: ${data.title}")
            }
        }
    }
}