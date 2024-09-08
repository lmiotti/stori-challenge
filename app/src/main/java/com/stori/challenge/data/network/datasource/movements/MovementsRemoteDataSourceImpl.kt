package com.stori.challenge.data.network.datasource.movements

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stori.challenge.data.network.model.MovementDTO
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MovementsRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): MovementsRemoteDataSource {

    override suspend fun getMovements(): Flow<Resource<List<MovementDTO>>> = flow {
        firebaseAuth.currentUser?.uid?.let { uid ->
            val response = try {
                val collection = firestore.collection("Movements")
                    .whereEqualTo("userId", uid)
                    .get()
                    .await()
                val movements = collection.documents.mapNotNull { it.toObject(MovementDTO::class.java) }
                Resource.Success(movements)
            } catch (e: Exception) {
                Log.e("ASD", "${e.message}")
                Resource.Failure(e.message)
            }
            emit(response)
        } ?: emit(Resource.Failure("Uid is null"))
    }
}