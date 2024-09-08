package com.stori.challenge.data.network.repository.profile

import android.net.Uri
import com.stori.challenge.data.network.model.ProfileDTO
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Resource<ProfileDTO>>

    suspend fun uploadImage(image: Uri?): Flow<Resource<String>>

    suspend fun updateProfile(
        form: RegistrationForm,
        imagePath: String
    ): Flow<Resource<Unit>>
}