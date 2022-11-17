package com.example.movieapps.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapps.data.local.entity.MovieNowPlayingKeysEntity

@Dao
interface MovieNowPlayingRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieNowPlayingRemoteKeys(remoteKey: List<MovieNowPlayingKeysEntity>?)

    @Query("SELECT * FROM MovieNowPlayingRemoteKeysTable WHERE id = :id")
    suspend fun getMovieNowPlayingRemoteKeysId(id: String): MovieNowPlayingKeysEntity?

    @Query("DELETE FROM MovieNowPlayingRemoteKeysTable")
    suspend fun deleteMovieNowPlayingRemoteKeys()
}