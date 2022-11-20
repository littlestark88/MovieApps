package com.example.movieapps.domain

import com.example.movieapps.domain.model.MovieList

class MovieInteractor(private val movieRepository: IMovieRepository): IMovieUseCase{

    override fun getMovieNowPlaying() = movieRepository.getMovieNowPlaying()

    override fun getMovieSimilar(movieId: Int) = movieRepository.getMovieSimilar(movieId)

    override fun getMovieNowPlayingFavorite() = movieRepository.getMovieNowPlayingFavorite()

    override suspend fun updateMovieNowPlayingFavorite(movie: MovieList, state: Boolean) =
        movieRepository.updateMovieNowPlayingFavorite(movie, state)
}