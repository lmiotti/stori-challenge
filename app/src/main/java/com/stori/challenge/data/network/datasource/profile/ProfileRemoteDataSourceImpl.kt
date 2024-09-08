package com.stori.challenge.data.network.datasource.profile

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.stori.challenge.data.network.model.ProfileDTO
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: StorageReference
): ProfileRemoteDataSource {

    override suspend fun uploadImage(image: Uri?): Flow<Resource<String>> = flow {
        image?.let {imageUri ->
            val imageRef = firebaseStorage.child("images/${UUID.randomUUID()}")
            val response = try {
                val taskSnapshot = imageRef.putFile(imageUri).await()
                val downloadUri = taskSnapshot.metadata?.reference?.downloadUrl?.await()
                Resource.Success(data = downloadUri.toString())
            } catch (e: Exception) {
                Resource.Failure(e.message)
            }
            emit(response)
        } ?: emit(Resource.Failure("ImageUri is null"))
    }

    override suspend fun updateProfile(
        name: String,
        surname: String,
        email: String,
        imagePath: String
    ): Flow<Resource<Unit>> = flow {
        firebaseAuth.currentUser?.uid?.let { uid ->
            val map = mutableMapOf(
                "uid" to uid,
                "name" to name,
                "surname" to surname,
                "email" to email,
                "imagePath" to imagePath
            )
            val response = try {
                firestore.collection("Users").add(map).await()
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Failure(e.message)
            }
            emit(response)
        } ?: emit(Resource.Failure("Uid is null"))
    }

    override fun getProfile(): Flow<Resource<ProfileDTO>> = flow {
        firebaseAuth.currentUser?.uid?.let { uid ->
            val response = try {
                val collection = firestore.collection("Users")
                    .whereEqualTo("uid", uid)
                    .get()
                    .await()
                Resource.Success(collection.documents.mapNotNull { it.toObject(ProfileDTO::class.java) }.first())
            } catch (e: Exception) {
                Resource.Failure(e.message)
            }
            emit(response)
        } ?: emit(Resource.Failure("Uid is null"))

    }
}