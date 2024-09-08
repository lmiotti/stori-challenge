package com.stori.challenge.domain.model

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class Failure<T>(error: String? = ""): Resource<T>(error = error)
    class Loading<T>: Resource<T>()

}
