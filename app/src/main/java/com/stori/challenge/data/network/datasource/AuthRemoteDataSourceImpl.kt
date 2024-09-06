package com.stori.challenge.data.network.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.stori.challenge.domain.model.NetworkError
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRemoteDataSource {

    override suspend fun signIn(email: String, password: String): Resource<AuthResult> {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return Resource.Success(result)
        } catch (e: Exception) {
            return Resource.Failure(NetworkError(message = e.message ?: ""))
        }
    }
}