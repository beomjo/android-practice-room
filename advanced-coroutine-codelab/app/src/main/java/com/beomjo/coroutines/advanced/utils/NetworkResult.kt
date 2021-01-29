package com.beomjo.coroutines.advanced.utils

sealed class NetworkResult<out T>

object Loading: NetworkResult<Nothing>()

data class OK<out T>(val data: T): NetworkResult<T>()

data class NetworkError(val exception: Throwable): NetworkResult<Nothing>()
