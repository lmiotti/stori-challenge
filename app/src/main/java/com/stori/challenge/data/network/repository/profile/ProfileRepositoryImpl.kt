package com.stori.challenge.data.network.repository.profile

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.stori.challenge.data.network.datasource.profile.ProfileRemoteDataSource
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource
): ProfileRepository {

    override fun getProfile(): Profile {
        return remoteDataSource.getProfile()
    }

    override suspend fun uploadImage(
        user: FirebaseUser?,
        image: Uri
    ): Flow<Resource<String>> = remoteDataSource.uploadImage(image)

    override suspend fun updateProfile(
        user: FirebaseUser?,
        form: RegistrationForm,
        imagePath: String
    ): Flow<Resource<Unit>> = remoteDataSource.updateProfile(user, form.name, form.surname, imagePath)
}