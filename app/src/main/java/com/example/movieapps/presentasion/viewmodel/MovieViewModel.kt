package com.example.movieapps.presentasion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapps.domain.IMovieUseCase
import com.example.movieapps.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

class MovieViewModel(
    private val movieUseCase: IMovieUseCase
): ViewModel() {

    fun getMovieNowPlaying(): LiveData<PagingData<MovieList>> =
        movieUseCase.getMovieNowPlaying().cachedIn(viewModelScope).asLiveData()

    fun getMovieSimilar(movieId: Int): LiveData<PagingData<MovieList>> =
        movieUseCase.getMovieSimilar(movieId).cachedIn(viewModelScope).asLiveData()

    suspend fun updateMovieNowPlayingFavorite(movie: MovieList, state: Boolean) =
        movieUseCase.updateMovieNowPlayingFavorite(movie, state)

    fun getMovieNowPlayingFavorite() =
        movieUseCase.getMovieNowPlayingFavorite().asLiveData()
}