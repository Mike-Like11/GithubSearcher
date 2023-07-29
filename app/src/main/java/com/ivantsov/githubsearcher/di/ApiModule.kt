package com.ivantsov.githubsearcher.di

import com.ivantsov.githubsearcher.data.remote.GithubService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(GithubService::class.java) }
}