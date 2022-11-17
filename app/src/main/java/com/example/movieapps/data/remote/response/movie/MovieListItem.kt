package com.example.movieapps.data.remote.response.movie

import com.google.gson.annotations.SerializedName

data class MovieListItem(

	@field:SerializedName("overview")
	val overview: String?,

	@field:SerializedName("original_language")
	val originalLanguage: String?,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = 0.0,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = 0.0,

	@field:SerializedName("id")
	val id: Int? = 0
)
