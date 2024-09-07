package com.stori.challenge.data.network.repository.auth

import android.net.Uri
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val isUserLogged: Boolean

    suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<Unit>>

    suspend fun createUser(
        form: RegistrationForm
    ): Flow<Resource<Unit>>

    fun getProfile(): Profile
    suspend fun signOut()
}