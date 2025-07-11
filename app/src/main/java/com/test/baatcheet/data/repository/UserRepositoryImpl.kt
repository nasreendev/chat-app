package com.test.baatcheet.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.model.UserModel
import com.test.baatcheet.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl : UserRepository {
    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun getAllUsers(): NetworkResponse<List<UserModel>> {

        return try {
            val snapshot = firestore.collection("users").get().await()
            val currentUserId = FirebaseAuth.getInstance().uid
            val users = snapshot.documents.mapNotNull { doc ->
                doc.toObject(UserModel::class.java)
            }.filter {
                it.id != currentUserId
            }
            NetworkResponse.Success(users)
        } catch (e: Exception) {
            NetworkResponse.Failure(e.localizedMessage ?: "")
        }

    }
}