package com.ivantsov.githubsearcher.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RepositorySearchResponse(
    val items: List<RepositoryInfo>
)