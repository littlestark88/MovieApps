package com.example.movieapps.domain

class MovieInteractor(private val movieRepository: IMovieRepository): IMovieUseCase{

    override fun getMovieNowPlaying() = movieRepository.getMovieNowPlaying()

    override fun getMovieSimilar(movieId: Int) = movieRepository.getMovieSimilar(movieId)
}