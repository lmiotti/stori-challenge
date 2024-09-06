package com.stori.challenge.data.network.datasource

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {

    suspend fun signIn(
        email: String,
        password: String
    ): Resource<AuthResult>
    suspend fun createUser(
        email: String,
        password: String,
    ): Resource<AuthResult>

    suspend fun updateProfile(
        user: FirebaseUser?,
        name: String,
        surname: String,
        photo: Uri
    ): Resource<Unit>
}