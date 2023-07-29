package com.ivantsov.githubsearcher.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryInfo(
    val id: Int,
    val name: String,
    val description: String?,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("forks_count")
    val forksCount: Int
)