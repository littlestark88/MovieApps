package com.example.movieapps.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapps.data.local.MovieDatabase
import com.example.movieapps.data.remote.MovieRemoteDataSource
import com.example.movieapps.data.remote.remotemediator.MovieNowPlayingRemoteMediator
import com.example.movieapps.data.remote.remotemediator.MovieSimilarRemoteMediator
import com.example.movieapps.domain.IMovieRepository
import com.example.movieapps.domain.model.MovieList
import com.example.movieapps.utils.mapper.MovieNowPlayingMapper
import com.example.movieapps.utils.mapper.MovieSimilarMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieDatabase: MovieDatabase
): IMovieRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMovieNowPlaying(): Flow<PagingData<MovieList>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = MovieNowPlayingRemoteMediator(movieDatabase, movieRemoteDataSource),
            pagingSourceFactory = {
                movieDatabase.movieNowPlayingDao().getMovieNowPlaying()
            }
        ).flow.map {
            MovieNowPlayingMapper.mapGetMovieNowPlayingPaging(it)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovieSimilar(movieId: Int): Flow<PagingData<MovieList>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = MovieSimilarRemoteMediator(movieDatabase, movieRemoteDataSource, movieId),
            pagingSourceFactory = {
                movieDatabase.movieSimilarDao().getMovieSimilar()
            }
        ).flow.map {
            MovieSimilarMapper.mapGetMovieSimilarPaging(it)
        }
    }
}