package com.stori.challenge.data.network.repository.profile

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Profile

    suspend fun uploadImage(
        user: FirebaseUser?,
        image: Uri
    ): Flow<Resource<String>>

    suspend fun updateProfile(
        user: FirebaseUser?,
        form: RegistrationForm,
        imagePath: String
    ): Flow<Resource<Unit>>
}