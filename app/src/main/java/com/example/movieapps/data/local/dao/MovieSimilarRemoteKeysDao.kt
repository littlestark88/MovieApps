package com.example.movieapps.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapps.data.local.entity.MovieSimilarKeysEntity

@Dao
interface MovieSimilarRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieSimilarRemoteKeys(remoteKey: List<MovieSimilarKeysEntity>?)

    @Query("SELECT * FROM MovieSimilarRemoteKeysEntityTable WHERE id = :id")
    suspend fun getMovieSimilarRemoteKeysId(id: String): MovieSimilarKeysEntity?

    @Query("DELETE FROM MovieSimilarRemoteKeysEntityTable")
    suspend fun deleteMovieSimilarRemoteKeys()
}