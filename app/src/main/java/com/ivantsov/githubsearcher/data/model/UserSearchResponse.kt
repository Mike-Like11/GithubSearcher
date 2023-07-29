package com.ivantsov.githubsearcher.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSearchResponse(
    val items: List<UserInfo>
)