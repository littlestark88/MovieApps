package com.example.movieapps.utils.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.example.movieapps.data.local.entity.MovieNowPlayingEntity
import com.example.movieapps.data.remote.response.movie.MovieListItem
import com.example.movieapps.domain.model.MovieList

object MovieNowPlayingMapper {

    fun mapGetMovieNowPlayingEntity(listData: List<MovieListItem>?): List<MovieNowPlayingEntity> =
        listData?.map {
            MovieNowPlayingEntity(
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

    fun mapGetMovieNowPlayingPaging(listData: PagingData<MovieNowPlayingEntity>): PagingData<MovieList> =
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

    fun mapMovieNowPlayingEntityToDomain(data: List<MovieNowPlayingEntity>): List<MovieList> =
        data.map {
            MovieList(
                overview = it.overview.orEmpty(),
                originalLanguage = it.originalLanguage.orEmpty(),
                title = it.title.orEmpty(),
                posterPath = it.posterPath.orEmpty(),
                backdropPath = it.backdropPath.orEmpty(),
                releaseDate = it.releaseDate.orEmpty(),
                popularity = it.popularity ?: 0.0,
                voteAverage = it.voteAverage ?: 0.0,
                id = it.id.toInt(),
                isFavorite = it.isFavorite ?: false
            )
        }

    fun mapMovieNowPlayingDomainToEntity(data: MovieList) = MovieNowPlayingEntity(
        id = data.id.toString(),
        overview = data.overview,
        originalLanguage = data.originalLanguage,
        title = data.title,
        posterPath = data.posterPath,
        backdropPath = data.backdropPath,
        releaseDate = data.releaseDate,
        popularity = data.popularity,
        voteAverage = data.voteAverage,
        isFavorite = data.isFavorite

    )
}