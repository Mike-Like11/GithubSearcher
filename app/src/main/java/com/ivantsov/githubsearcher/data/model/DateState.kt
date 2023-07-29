package com.ivantsov.githubsearcher.data.model

sealed class DateState<out T> {
    object Loading : DateState<Nothing>()
    class Success<T>(val data: T) : DateState<T>()
    class Failure(val error: Throwable) : DateState<Nothing>()
}
