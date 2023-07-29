package com.ivantsov.githubsearcher.di

import android.app.Application
import com.ivantsov.githubsearcher.data.remote.LiveNetworkMonitor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import java.io.IOException

val networkModule = module {
    val contentType = MediaType.get("application/json")
    val json = Json {
        ignoreUnknownKeys = true
    }

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideNetworkMonitor(application: Application): LiveNetworkMonitor =
        LiveNetworkMonitor(application.applicationContext)

    fun provideHttpClient(cache: Cache, liveNetworkMonitor: LiveNetworkMonitor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor {
                if (liveNetworkMonitor.isConnected) {
                    it.proceed(it.request())
                } else {
                    throw IOException("Нет подключения к интернету")
                }
            }
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideBaseUrl(): String = "https://api.github.com/"

    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideNetworkMonitor(get()) }
    single { provideHttpClient(get(), get()) }
    single { provideBaseUrl() }
    single { provideRetrofit(get(), get()) }
}