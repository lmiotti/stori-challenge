package com.stori.challenge.data.network.datasource.profile

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRemoteDataSource {

    suspend fun uploadImage(image: Uri): Flow<Resource<String>>
    suspend fun updateProfile(
        user: FirebaseUser?,
        name: String,
        surname: String,
        imagePath: String
    ): Flow<Resource<Unit>>

    fun getProfile(): Profile
}