package com.stori.challenge.data.network.datasource.auth

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {

    val user: FirebaseUser?

    suspend fun signIn(
        email: String,
        password: String
    ): Resource<AuthResult>

    suspend fun createUser(
        email: String,
        password: String,
    ): Resource<AuthResult>

    suspend fun register(
        uid: String
    ): Resource<Unit>

    suspend fun updateProfile(
        user: FirebaseUser?,
        name: String,
        surname: String,
        photo: Uri
    ): Resource<Unit>

    fun getProfile(): Profile

    suspend fun signOut()
}