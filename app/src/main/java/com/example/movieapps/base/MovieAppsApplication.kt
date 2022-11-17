package com.example.movieapps.base

import android.app.Application
import com.example.movieapps.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieAppsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MovieAppsApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    movieRemoteMediatorModule
                )
            )
        }
    }
}