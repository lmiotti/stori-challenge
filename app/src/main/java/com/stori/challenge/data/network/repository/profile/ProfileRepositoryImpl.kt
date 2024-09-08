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

    override fun getProfile(): Flow<Resource<Profile>> {
        return remoteDataSource.getProfile()
    }

    override suspend fun uploadImage(image: Uri?): Flow<Resource<String>> =
        remoteDataSource.uploadImage(image)

    override suspend fun updateProfile(
        form: RegistrationForm,
        imagePath: String
    ): Flow<Resource<Unit>> = remoteDataSource.updateProfile(form.name, form.surname, form.email, imagePath)
}