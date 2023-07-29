package com.ivantsov.githubsearcher

import android.app.Application
import com.ivantsov.githubsearcher.di.apiModule
import com.ivantsov.githubsearcher.di.networkModule
import com.ivantsov.githubsearcher.di.repositoryModule
import com.ivantsov.githubsearcher.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GithubSearcherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GithubSearcherApplication)
            modules(apiModule, networkModule, repositoryModule, viewModelModule)
        }
    }
}