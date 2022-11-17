package com.example.movieapps.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapps.data.local.entity.MovieSimilarEntity

@Dao
interface MovieSimilarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieSimilar(movieSimilarEntity: List<MovieSimilarEntity>)

    @Query("SELECT * FROM MovieSimilarTable")
    fun getMovieSimilar(): PagingSource<Int, MovieSimilarEntity>

    @Query("DELETE FROM MovieSimilarTable")
    suspend fun deleteMovieSimilar()
}