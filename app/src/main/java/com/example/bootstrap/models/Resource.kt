package com.example.bootstrap.models

sealed class Resource<T>(
    val data: T? = null,
    val error: NetworkError? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class Failure<T>(networkError: NetworkError): Resource<T>(error = networkError)
}

data class NetworkError(val code: Int? = null, val message: String)