package com.example.movieapps.di

import com.example.movieapps.domain.IMovieUseCase
import com.example.movieapps.domain.MovieInteractor
import com.example.movieapps.presentasion.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
}