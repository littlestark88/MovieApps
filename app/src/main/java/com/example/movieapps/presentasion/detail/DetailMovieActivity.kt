package com.example.movieapps.presentasion.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapps.BuildConfig.POSTER_URL
import com.example.movieapps.R
import com.example.movieapps.databinding.ActivityDetailMovieBinding
import com.example.movieapps.domain.model.MovieList
import com.example.movieapps.presentasion.adapter.LoadingStateAdapter
import com.example.movieapps.presentasion.home.HomeActivity
import com.example.movieapps.presentasion.viewmodel.MovieViewModel
import com.example.movieapps.utils.Const.DATA_MOVIE
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val movieViewModel: MovieViewModel by inject()
    private val movieSimilarAdapter: MovieSimilarAdapter by lazy {
        MovieSimilarAdapter(
            onClickListener = {

            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<MovieList>(DATA_MOVIE)
        data?.id?.let {
            movieViewModel.getMovieSimilar(it).observe(this) { movieData ->
                movieSimilarAdapter.submitData(lifecycle, movieData)
            }
        }
        showMovieSimilarRecycleList()
        initDetailMovie(data)
    }

    private fun initDetailMovie(data: MovieList?) {
        with(binding) {
            tvTitle.text = data?.title
            tvOverviewDesc.text = data?.overview
            tvRating.text = data?.voteAverage.toString()
            Glide
                .with(this@DetailMovieActivity)
                .load(POSTER_URL + data?.posterPath)
                .placeholder(R.drawable.ic_refresh)
                .into(imgPoster)
            Glide
                .with(this@DetailMovieActivity)
                .load(POSTER_URL + data?.backdropPath)
                .placeholder(R.drawable.ic_refresh)
                .into(imgBackImage)
            Toast.makeText(this@DetailMovieActivity, data?.isFavorite.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this@DetailMovieActivity, data?.title, Toast.LENGTH_SHORT).show()
            data?.isFavorite?.let { setStatusFavorite(it) }
            cvFavorite.setOnClickListener {
                setFavoriteMovie(data)
            }
        }
    }

    private fun setFavoriteMovie(data: MovieList?) {
        data?.let {
            var favoriteMovieState = data.isFavorite
            favoriteMovieState = !favoriteMovieState
            lifecycleScope.launch {
                movieViewModel.updateMovieNowPlayingFavorite(data, favoriteMovieState)
            }
            setStatusFavorite(favoriteMovieState)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if(statusFavorite) {
            binding.imgFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.imgFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_unfavorite
                )
            )
        }
    }

    private fun showMovieSimilarRecycleList() {
        binding.rvMovieSimilar.apply {
            layoutManager = LinearLayoutManager(this@DetailMovieActivity)
            setHasFixedSize(true)
            adapter = movieSimilarAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    movieSimilarAdapter.refresh()
                }
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}