package com.test.baatcheet.data.repository

import android.util.Log
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

    override suspend fun getLoggedInUser(): NetworkResponse<UserModel> {
        return try {
            val uid = FirebaseAuth.getInstance().uid
            if (uid != null) {
                val doc = firestore.collection("users")
                    .document(uid)
                    .get()
                    .await()
                val user = doc.toObject(UserModel::class.java)
                if (user != null) {
                    return NetworkResponse.Success(user)
                } else {
                    NetworkResponse.Failure("User not found")
                }
            } else {
                NetworkResponse.Failure("User not logged in")
            }

        } catch (e: Exception) {
            NetworkResponse.Failure(e.localizedMessage ?: "Something went wrong")
        }
    }

    override suspend fun addFriend(friendId: String): NetworkResponse<UserModel> {
        return try {
            val currentUserId = FirebaseAuth.getInstance().uid
                ?: return NetworkResponse.Failure("User not logged in")

            val friend = firestore.collection("users")
                .document(friendId)
                .get()
                .await()
                .toObject(UserModel::class.java)
                ?: return NetworkResponse.Failure("User not found")

            firestore.collection("users")
                .document(currentUserId)
                .collection("friends")
                .document(friendId)
                .set(friend)
                .await()
            NetworkResponse.Success(friend)
        } catch (e: Exception) {
            NetworkResponse.Failure(e.localizedMessage ?: "Something went wrong")
        }
    }


    override suspend fun getAllFriends(): NetworkResponse<List<UserModel>> {
        return try {
            val currentUserId = FirebaseAuth.getInstance().uid
                ?: return NetworkResponse.Failure("User not logged in")

            val snapshot = firestore.collection("users")
                .document(currentUserId)
                .collection("friends")
                .get()
                .await()

            val friends = snapshot.toObjects(UserModel::class.java)
            NetworkResponse.Success(friends)
        } catch (e: Exception) {
            NetworkResponse.Failure(e.localizedMessage ?: "Something went wrong")
        }
    }
}