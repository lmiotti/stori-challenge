package com.stori.challenge.data.network.repository.auth

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val isUserLogged: Boolean

    suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>>

    suspend fun createUser(
        name: String,
        password: String
    ): Flow<Resource<AuthResult>>

    fun signOut()
}