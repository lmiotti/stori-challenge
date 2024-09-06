package com.stori.challenge.domain.model

sealed class Resource<T>(
    val data: T? = null,
    val error: NetworkError? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class Failure<T>(networkError: NetworkError): Resource<T>(error = networkError)
    class Loading<T>(): Resource<T>()

}

data class NetworkError(val code: Int? = null, val message: String)