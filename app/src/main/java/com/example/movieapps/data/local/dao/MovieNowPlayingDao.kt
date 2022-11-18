package com.example.movieapps.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.movieapps.data.local.entity.MovieNowPlayingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieNowPlayingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieNowPlaying(movieNowPlayingEntity: List<MovieNowPlayingEntity>)

    @Query("SELECT * FROM MovieNowPlayingTable")
    fun getMovieNowPlaying(): PagingSource<Int, MovieNowPlayingEntity>

    @Query("DELETE FROM MovieNowPlayingTable")
    suspend fun deleteMovieNowPlaying()

    @Update
    suspend fun updateMovieNowPlaying(movieNowPlayingEntity: MovieNowPlayingEntity)

    @Query("SELECT * FROM MovieNowPlayingTable where isFavorite = 1")
    fun getFavoriteMovieNowPlaying(): Flow<List<MovieNowPlayingEntity>>
}