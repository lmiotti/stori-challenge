package com.stori.challenge.data.network.datasource

import com.google.firebase.auth.AuthResult
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {

    suspend fun signIn(email: String, password: String): Resource<AuthResult>
}