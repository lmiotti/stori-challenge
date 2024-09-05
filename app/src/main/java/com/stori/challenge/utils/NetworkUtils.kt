package com.stori.challenge.utils

import com.stori.challenge.models.NetworkError
import com.stori.challenge.models.Resource
import retrofit2.Response

object NetworkUtils {

    suspend fun <T> safeApiCall(block: suspend () -> Response<T>): Resource<T> {
        try {
            val response = block.invoke()
            return if (response.isSuccessful) {
                Resource.Success(data = response.body()!!)
            } else {
                val error = NetworkError(response.code(), response.message())
                Resource.Failure(networkError = error)
            }
        } catch (e: Exception) {
            val error = NetworkError(message = e.message ?: "")
            return Resource.Failure(networkError = error)
        }
    }
}