package com.example.movieapps.data.local

import com.example.movieapps.data.local.dao.MovieNowPlayingDao
import com.example.movieapps.data.local.entity.MovieNowPlayingEntity
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(private val movieNowPlayingDao: MovieNowPlayingDao) {

    fun getFavoriteMovieNowPlaying(): Flow<List<MovieNowPlayingEntity>> = movieNowPlayingDao.getFavoriteMovieNowPlaying()

    suspend fun setFavoriteMovie(data: MovieNowPlayingEntity, newState: Boolean) {
        data.isFavorite = newState
        movieNowPlayingDao.updateMovieNowPlaying(data)
    }
}