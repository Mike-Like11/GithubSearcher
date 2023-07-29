package com.ivantsov.githubsearcher.di

import com.ivantsov.githubsearcher.ui.mainSearch.SearchViewModel
import com.ivantsov.githubsearcher.ui.repositoryContent.RepositoryContentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { RepositoryContentViewModel(get()) }
}