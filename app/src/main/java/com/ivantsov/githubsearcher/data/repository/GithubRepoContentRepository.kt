package com.ivantsov.githubsearcher.data.repository

import com.ivantsov.githubsearcher.data.model.FolderContentItem

interface GithubRepoContentRepository {
    suspend fun getContentByPath(repository: String, path: String): List<FolderContentItem>
}