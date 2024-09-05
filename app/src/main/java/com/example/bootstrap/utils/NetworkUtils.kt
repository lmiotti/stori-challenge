package com.example.bootstrap.utils

import com.example.bootstrap.models.NetworkError
import com.example.bootstrap.models.Resource
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