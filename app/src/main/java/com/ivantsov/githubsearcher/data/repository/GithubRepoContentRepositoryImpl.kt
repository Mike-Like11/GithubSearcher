package com.ivantsov.githubsearcher.data.repository

import android.util.Log
import com.ivantsov.githubsearcher.data.remote.GithubService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepoContentRepositoryImpl(
    private val githubService: GithubService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GithubRepoContentRepository {
    override suspend fun getContentByPath(repository: String, path: String) =
        withContext(dispatcher) {
            Log.d("testim", repository + path)
            githubService.getFolderContentByPathInRepository(
                repository.substringBefore("/"),
                repository.substringAfter("/"),
                path
            )
        }
}