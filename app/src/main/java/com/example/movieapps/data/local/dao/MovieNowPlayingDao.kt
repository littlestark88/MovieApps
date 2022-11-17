package com.example.movieapps.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapps.data.local.entity.MovieNowPlayingEntity

@Dao
interface MovieNowPlayingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieNowPlaying(movieNowPlayingEntity: List<MovieNowPlayingEntity>)

    @Query("SELECT * FROM MovieNowPlayingTable")
    fun getMovieNowPlaying(): PagingSource<Int, MovieNowPlayingEntity>

    @Query("DELETE FROM MovieNowPlayingTable")
    suspend fun deleteMovieNowPlaying()
}