package com.ivantsov.githubsearcher.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FolderContentItem(
    val name: String,
    @SerialName("html_url")
    val htmlUrl: String,
    val path: String,
    val type: String
)