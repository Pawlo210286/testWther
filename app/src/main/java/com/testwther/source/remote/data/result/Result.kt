package com.testwther.source.remote.data.result

sealed class Result<T> {
    class Success<T>(
        val data: T
    ) : Result<T>()

    class Error<T>(
        val error: Exception
    ) : Result<T>()
}