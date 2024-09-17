package com.client.android.core_base

sealed class AppResult<out T, out E> {
    data class Success<out T>(val data: T) : AppResult<T, Nothing>()
    data class Error<out E>(val error: E) : AppResult<Nothing, E>()
}
