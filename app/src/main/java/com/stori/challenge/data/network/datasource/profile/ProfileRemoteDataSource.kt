package com.stori.challenge.data.network.datasource.profile

import android.net.Uri
import com.stori.challenge.data.network.model.ProfileDTO
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRemoteDataSource {

    suspend fun uploadImage(image: Uri?): Flow<Resource<String>>
    suspend fun updateProfile(
        name: String,
        surname: String,
        email: String,
        imagePath: String
    ): Flow<Resource<Unit>>

    fun getProfile(): Flow<Resource<ProfileDTO>>
}