package com.example.movieapps.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapps.data.remote.network.ApiService
import com.example.movieapps.data.remote.response.movie.MovieListItem
import com.example.movieapps.utils.Const.INITIAL_PAGE_INDEX
import java.lang.Exception

class MoviePagingSource(private val apiService: ApiService): PagingSource<Int, MovieListItem>() {

    override fun getRefreshKey(state: PagingState<Int, MovieListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMovieNowPlaying(position.toString())

            LoadResult.Page(
                data = responseData.movieList,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.movieList.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}