package com.example.movieapps.presentasion.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.movieapps.BuildConfig.POSTER_URL
import com.example.movieapps.R
import com.example.movieapps.databinding.ActivityDetailMovieBinding
import com.example.movieapps.domain.model.MovieList
import com.example.movieapps.presentasion.home.HomeActivity
import com.example.movieapps.utils.Const.DATA_MOVIE

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<MovieList>(DATA_MOVIE)
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
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}