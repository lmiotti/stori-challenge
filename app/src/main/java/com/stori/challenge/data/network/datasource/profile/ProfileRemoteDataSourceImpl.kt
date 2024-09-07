package com.stori.challenge.data.network.datasource.profile

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.domain.model.NetworkError
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: StorageReference
): ProfileRemoteDataSource {

    override suspend fun uploadImage(image: Uri): Flow<Resource<String>> = flow {
        val imageRef = firebaseStorage.child("images/${UUID.randomUUID()}")
        val result = try {
            val taskSnapshot = imageRef.putFile(image).await()
            val downloadUri = taskSnapshot.metadata?.reference?.downloadUrl?.await()
            Resource.Success(data = downloadUri.toString())
        } catch (e: Exception) {
            Resource.Failure(networkError = NetworkError(message = e.message ?: ""))
        }
        emit(result)
    }

    override suspend fun updateProfile(
        user: FirebaseUser?,
        name: String,
        surname: String,
        imagePath: String
    ): Flow<Resource<Unit>> = flow {
        val map = mutableMapOf(
            "uid" to user?.uid,
            "name" to name,
            "surname" to surname,
            "imagePath" to imagePath
        )

        val result = try {
            firestore.collection("Users").add(map).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(NetworkError(message = e.message ?: ""))
        }
        emit(result)
    }

    override fun getProfile(): Profile {
        //val user = firebaseAuth.currentUser
        return Profile("", Uri.parse(""))
    }
}