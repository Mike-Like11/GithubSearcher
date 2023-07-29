package com.ivantsov.githubsearcher.data.repository

import android.util.Log
import com.ivantsov.githubsearcher.data.remote.GithubService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepositoryImpl(
    private val githubService: GithubService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SearchRepository {
    override suspend fun searchUsers(query: String) = withContext(dispatcher) {
        Log.d("testim", Thread.currentThread().name)
        githubService.searchUsers(query).items
    }

    override suspend fun searchRepositories(query: String) = withContext(dispatcher) {
        githubService.searchRepositories(query).items
    }
}