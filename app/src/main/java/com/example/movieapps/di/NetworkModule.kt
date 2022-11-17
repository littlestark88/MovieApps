package com.example.movieapps.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.movieapps.BuildConfig.BASE_URL
import com.example.movieapps.data.MovieRepository
import com.example.movieapps.data.local.MovieDatabase
import com.example.movieapps.data.remote.network.ApiService
import com.example.movieapps.data.remote.remotemediator.MovieNowPlayingRemoteMediator
import com.example.movieapps.data.remote.remotemediator.MovieSimilarRemoteMediator
import com.example.movieapps.domain.IMovieRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        val chuckerInterceptor = ChuckerInterceptor.Builder(androidContext())
            .collector(chuckerCollector)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()

        OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    factory { get<MovieDatabase>().movieNowPlayingDao() }
    factory { get<MovieDatabase>().movieNowPlayingRemoteKeysDao() }
    factory { get<MovieDatabase>().movieSimilarDao() }
    factory { get<MovieDatabase>().movieSimilarRemoteKeysDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Movie.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val repositoryModule = module {
    factory<IMovieRepository> { MovieRepository(get(),get()) }
}

val movieRemoteMediatorModule = module {
    factory { MovieNowPlayingRemoteMediator(get(), get()) }
    factory { MovieSimilarRemoteMediator(get(), get(), get()) }
}