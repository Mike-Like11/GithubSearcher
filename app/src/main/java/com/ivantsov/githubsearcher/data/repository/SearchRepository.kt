package com.ivantsov.githubsearcher.data.repository

import com.ivantsov.githubsearcher.data.model.RepositoryInfo
import com.ivantsov.githubsearcher.data.model.UserInfo

interface SearchRepository {
    suspend fun searchUsers(query: String): List<UserInfo>
    suspend fun searchRepositories(query: String): List<RepositoryInfo>
}