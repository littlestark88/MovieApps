package com.example.movieapps.data.remote.remotemediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapps.data.local.MovieDatabase
import com.example.movieapps.data.local.entity.MovieNowPlayingEntity
import com.example.movieapps.data.local.entity.MovieNowPlayingKeysEntity
import com.example.movieapps.data.remote.MovieRemoteDataSource
import com.example.movieapps.data.remote.response.movie.MovieListItem
import com.example.movieapps.utils.Const.INITIAL_PAGE_INDEX
import com.example.movieapps.utils.mapper.MovieNowPlayingMapper
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalPagingApi::class)
class MovieNowPlayingRemoteMediator(
    private val database: MovieDatabase,
    private val movieRemoteDataSource: MovieRemoteDataSource
): RemoteMediator<Int, MovieNowPlayingEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieNowPlayingEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                Log.e("movieRemoteMediator", "load: Refresh")
                val remoteKeys = getMovieNowPlayingRemoteKeysClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                Log.e("movieRemoteMediator", "load: Prepend")
                val remoteKeys = getMovieNowPlayingRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                Log.e("movieRemoteMediator", "load: Append")
                val remoteKeys = getMovieNowPlayingRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            Log.e("movieRemoteMediator", "load: 1")
            val responseData = movieRemoteDataSource.getMovieNowPlaying(page)
            var endOfPaginationReacted = false
            responseData.collect {
                endOfPaginationReacted = it.movieList.isEmpty() == true
            }
            database.withTransaction {
                Log.e("movieRemoteMediator", "load: 2")
                if(loadType == LoadType.REFRESH) {
                    Log.e("movieRemoteMediator", "load: 3")
//                    database.movieNowPlayingRemoteKeysDao().deleteMovieNowPlayingRemoteKeys()
//                    database.movieNowPlayingDao().deleteMovieNowPlaying()
                    database.movieNowPlayingDao().getMovieNowPlaying()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReacted) null else page + 1
                var keys = listOf<MovieNowPlayingKeysEntity>()
                Log.e("movieRemoteMediator", "load: 4")
                responseData.collect {
                    Log.e("movieRemoteMediator", "load: 5")
                    keys = it.movieList.map { movieList ->
                        MovieNowPlayingKeysEntity(id = movieList.id.toString(), prevKey = prevKey, nextKey = nextKey)
                    }
                }
                database.movieNowPlayingRemoteKeysDao().insertMovieNowPlayingRemoteKeys(keys)
                var movieList = listOf<MovieListItem>()
                responseData.collect {
                    movieList = it.movieList
                }
                database.movieNowPlayingDao().insertMovieNowPlaying(MovieNowPlayingMapper.mapGetMovieNowPlayingEntity(movieList))
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReacted)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getMovieNowPlayingRemoteKeysForLastItem(state: PagingState<Int, MovieNowPlayingEntity>): MovieNowPlayingKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.movieNowPlayingRemoteKeysDao().getMovieNowPlayingRemoteKeysId(data.id)
        }
    }

    private suspend fun getMovieNowPlayingRemoteKeysForFirstItem(state: PagingState<Int, MovieNowPlayingEntity>): MovieNowPlayingKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.movieNowPlayingRemoteKeysDao().getMovieNowPlayingRemoteKeysId(data.id)
        }
    }

    private suspend fun getMovieNowPlayingRemoteKeysClosestToCurrentPosition(state: PagingState<Int, MovieNowPlayingEntity>): MovieNowPlayingKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.movieNowPlayingRemoteKeysDao().getMovieNowPlayingRemoteKeysId(id)
            }
        }
    }
}