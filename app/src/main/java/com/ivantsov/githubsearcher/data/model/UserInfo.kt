package com.ivantsov.githubsearcher.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Int,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val score: Double,
    @SerialName("html_url")
    val htmlUrl: String
)