package com.example.movieapps.data.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    var movieList: List<MovieListItem>
)
