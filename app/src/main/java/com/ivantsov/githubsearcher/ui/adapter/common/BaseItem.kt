package com.ivantsov.githubsearcher.ui.adapter.common

open class BaseItem(
    private val id: Int, private val value: Any, val name: String
) {
    fun content(): Any = value
    fun id(): Int = id
    fun compareToOther(other: BaseItem): Boolean = other.content() == content()
}