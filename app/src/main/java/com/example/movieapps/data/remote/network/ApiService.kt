package com.example.movieapps.data.remote.network

import com.example.movieapps.BuildConfig.API_KEY
import com.example.movieapps.data.remote.response.movie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("page") page: Int? = 0
    ): MovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getMovieSimilar(
        @Query("api_key") apiKey: String? = API_KEY,
        @Query("page") page: Int? = 0,
        @Query("movie_id") movieId: Int? = 0
    ): MovieResponse
}