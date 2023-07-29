package com.ivantsov.githubsearcher.di

import com.ivantsov.githubsearcher.data.remote.GithubService
import com.ivantsov.githubsearcher.data.repository.GithubRepoContentRepository
import com.ivantsov.githubsearcher.data.repository.GithubRepoContentRepositoryImpl
import com.ivantsov.githubsearcher.data.repository.SearchRepository
import com.ivantsov.githubsearcher.data.repository.SearchRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    fun provideGithubRepoContentRepository(githubService: GithubService): GithubRepoContentRepository =
        GithubRepoContentRepositoryImpl(githubService)

    fun provideSearchRepository(githubService: GithubService): SearchRepository =
        SearchRepositoryImpl(githubService)
    single { provideGithubRepoContentRepository(get()) }
    single { provideSearchRepository(get()) }
}