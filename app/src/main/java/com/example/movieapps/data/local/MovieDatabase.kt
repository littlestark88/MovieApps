package com.example.movieapps.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapps.data.local.dao.MovieNowPlayingDao
import com.example.movieapps.data.local.dao.MovieNowPlayingRemoteKeysDao
import com.example.movieapps.data.local.dao.MovieSimilarDao
import com.example.movieapps.data.local.dao.MovieSimilarRemoteKeysDao
import com.example.movieapps.data.local.entity.MovieNowPlayingEntity
import com.example.movieapps.data.local.entity.MovieNowPlayingKeysEntity
import com.example.movieapps.data.local.entity.MovieSimilarEntity
import com.example.movieapps.data.local.entity.MovieSimilarKeysEntity

@Database(
    entities = [
        MovieNowPlayingEntity::class,
        MovieNowPlayingKeysEntity::class,
        MovieSimilarEntity::class,
        MovieSimilarKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieNowPlayingDao(): MovieNowPlayingDao
    abstract fun movieNowPlayingRemoteKeysDao(): MovieNowPlayingRemoteKeysDao
    abstract fun movieSimilarDao(): MovieSimilarDao
    abstract fun movieSimilarRemoteKeysDao(): MovieSimilarRemoteKeysDao

}