package com.example.movieapps.data.remote

import android.util.Log
import com.example.movieapps.data.remote.network.ApiService
import com.example.movieapps.data.remote.response.movie.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class MovieRemoteDataSource(
    private val apiService: ApiService
) {

    suspend fun getMovieNowPlaying(page: Int): Flow<MovieResponse> {
        return flow {
            try {
                val data = apiService.getMovieNowPlaying(page = page)
                emit(data)
            } catch (e: Exception) {
                Log.e( "getMovieNowPlaying: ", e.toString() )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieSimilar(page: Int, movieId: Int): Flow<MovieResponse> {
        return flow {
            try {
                val data = apiService.getMovieSimilar(
                    page = page,
                    movieId = movieId
                )
                emit(data)
            } catch (e: Exception) {
                Log.e( "getMovieSimilar: ", e.toString() )
            }
        }.flowOn(Dispatchers.IO)
    }
}