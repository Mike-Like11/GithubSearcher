package com.ivantsov.githubsearcher.data.remote

import com.ivantsov.githubsearcher.data.model.FolderContentItem
import com.ivantsov.githubsearcher.data.model.RepositorySearchResponse
import com.ivantsov.githubsearcher.data.model.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
    ): UserSearchResponse

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
    ): RepositorySearchResponse

    @GET("repos/{user}/{repository}/contents/{path}")
    suspend fun getFolderContentByPathInRepository(
        @Path("user") user: String,
        @Path("repository") repository: String,
        @Path("path") path: String
    ): List<FolderContentItem>
}