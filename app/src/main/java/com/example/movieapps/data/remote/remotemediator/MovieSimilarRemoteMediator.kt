package com.example.movieapps.data.remote.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movieapps.data.local.MovieDatabase
import com.example.movieapps.data.local.entity.MovieSimilarEntity
import com.example.movieapps.data.local.entity.MovieSimilarKeysEntity
import com.example.movieapps.data.remote.MovieRemoteDataSource
import com.example.movieapps.data.remote.response.movie.MovieListItem
import com.example.movieapps.utils.Const
import com.example.movieapps.utils.mapper.MovieSimilarMapper

@OptIn(ExperimentalPagingApi::class)
class MovieSimilarRemoteMediator(
    private val database: MovieDatabase,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieId: Int
): RemoteMediator<Int, MovieSimilarEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieSimilarEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getMovieSimilarRemoteKeysClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Const.INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getMovieSimilarRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getMovieSimilarRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val responseData = movieRemoteDataSource.getMovieSimilar(page, movieId)
            var endOfPaginationReacted = false
            responseData.collect {
                endOfPaginationReacted = it.movieList.isEmpty() == true
            }
            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    database.movieSimilarRemoteKeysDao().deleteMovieSimilarRemoteKeys()
                    database.movieSimilarDao().deleteMovieSimilar()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReacted) null else page + 1
                var keys = listOf<MovieSimilarKeysEntity>()
                responseData.collect {
                    keys = it.movieList.map { movieList ->
                        MovieSimilarKeysEntity(id = movieList.id.toString(), prevKey = prevKey, nextKey = nextKey)
                    }
                }
                database.movieSimilarRemoteKeysDao().insertMovieSimilarRemoteKeys(keys)
                var movieList = listOf<MovieListItem>()
                responseData.collect {
                    movieList = it.movieList
                }
                database.movieSimilarDao().insertMovieSimilar(MovieSimilarMapper.mapGetMovieSimilarEntity(movieList))
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReacted)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getMovieSimilarRemoteKeysForLastItem(state: PagingState<Int, MovieSimilarEntity>): MovieSimilarKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.movieSimilarRemoteKeysDao().getMovieSimilarRemoteKeysId(data.id)
        }
    }

    private suspend fun getMovieSimilarRemoteKeysForFirstItem(state: PagingState<Int, MovieSimilarEntity>): MovieSimilarKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.movieSimilarRemoteKeysDao().getMovieSimilarRemoteKeysId(data.id)
        }
    }

    private suspend fun getMovieSimilarRemoteKeysClosestToCurrentPosition(state: PagingState<Int, MovieSimilarEntity>): MovieSimilarKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.movieSimilarRemoteKeysDao().getMovieSimilarRemoteKeysId(id)
            }
        }
    }
}