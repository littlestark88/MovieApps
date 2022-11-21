package com.example.movieapps.presentasion.favoritemovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapps.BuildConfig.POSTER_URL
import com.example.movieapps.R
import com.example.movieapps.databinding.MovieFavoriteListItemBinding
import com.example.movieapps.domain.model.MovieList

class MovieFavoriteAdapter(
    private val data: MutableList<MovieList>,
    private val onClickListener: (MovieList) -> Unit
): RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteViewHolder>() {

    fun setFavoriteMovie(movie: List<MovieList>) {
        data.clear()
        data.addAll(movie)
        notifyItemChanged(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteViewHolder {
        val binding = MovieFavoriteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieFavoriteViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class MovieFavoriteViewHolder(private val binding: MovieFavoriteListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (data: MovieList) {
            with(binding) {
                tvTitle.text = data.title
                tvDescription.text = data.overview
                tvReleaseDate.text = data.releaseDate
                tvRating.text = data.voteAverage.toString()
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