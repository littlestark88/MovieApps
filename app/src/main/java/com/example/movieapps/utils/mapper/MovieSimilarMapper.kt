package com.example.movieapps.utils.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.example.movieapps.data.local.entity.MovieSimilarEntity
import com.example.movieapps.data.remote.response.movie.MovieListItem
import com.example.movieapps.domain.model.MovieList

object MovieSimilarMapper {
    fun mapGetMovieSimilarEntity(listData: List<MovieListItem>?): List<MovieSimilarEntity> =
        listData?.map {
            MovieSimilarEntity(
                id = it.id.toString(),
                overview = it.overview.orEmpty(),
                originalLanguage = it.originalLanguage.orEmpty(),
                title = it.title.orEmpty(),
                posterPath = it.posterPath.orEmpty(),
                backdropPath = it.backdropPath.orEmpty(),
                releaseDate = it.releaseDate.orEmpty(),
                popularity = it.popularity ?: 0.0,
                voteAverage = it.voteAverage ?: 0.0,
                isFavorite = false
            )
        } ?: emptyList()

    fun mapGetMovieSimilarPaging(listData: PagingData<MovieSimilarEntity>): PagingData<MovieList> =
        listData.map {
            MovieList(
                id = it.id.toInt(),
                overview = it.overview.orEmpty(),
                originalLanguage = it.originalLanguage.orEmpty(),
                title = it.title.orEmpty(),
                posterPath = it.posterPath.orEmpty(),
                backdropPath = it.backdropPath.orEmpty(),
                releaseDate = it.releaseDate.orEmpty(),
                popularity = it.popularity ?: 0.0,
                voteAverage = it.voteAverage ?: 0.0,
                isFavorite = false
            )
        }
}