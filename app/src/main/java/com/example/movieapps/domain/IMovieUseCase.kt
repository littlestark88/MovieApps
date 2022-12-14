package com.example.movieapps.domain

import androidx.paging.PagingData
import com.example.movieapps.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

interface IMovieUseCase {
    fun getMovieNowPlaying(): Flow<PagingData<MovieList>>
    fun getMovieSimilar(movieId: Int): Flow<PagingData<MovieList>>
    fun getMovieNowPlayingFavorite(): Flow<List<MovieList>>
    suspend fun updateMovieNowPlayingFavorite(movie: MovieList, state: Boolean)
}