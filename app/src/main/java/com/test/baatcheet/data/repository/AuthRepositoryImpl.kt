package com.test.baatcheet.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.model.UserModel
import com.test.baatcheet.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun signUp(
        email: String,
        password: String,
    ): NetworkResponse<FirebaseUser> {
        return try {
            withContext(Dispatchers.IO) {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                NetworkResponse.Success(result.user!!)
            }
        } catch (e: Exception) {
            NetworkResponse.Failure(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    override suspend fun signIn(
        email: String,
        password: String,
    ): NetworkResponse<FirebaseUser> {
        return try {
            withContext(Dispatchers.IO) {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                NetworkResponse.Success(result.user!!)
            }
        } catch (e: Exception) {
            NetworkResponse.Failure(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    override suspend fun saveUserInDb(
        uid: String,
        name: String,
        password: String,
        email: String,
    ): NetworkResponse<Boolean> {
        return try {
            val model = UserModel(
                name = name,
                password = password,
                id = uid,
                email = email
            )
            firestore.collection("users")
                .document(uid)
                .set(model)
                .await()
            NetworkResponse.Success(true)
        } catch (e: Exception) {
            NetworkResponse.Failure(e.localizedMessage ?: "")
        }
    }

    override suspend fun setDisplayName(name: String): NetworkResponse<Boolean> {
        return try {
            val user = auth.currentUser
            if (user == null) {
                return NetworkResponse.Failure("No user logged in")
            }
            withContext(Dispatchers.IO) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                user.updateProfile(profileUpdates).await()
                user.reload().await()
                firestore.collection("users").document(user.uid)
                    .update("name", name).await()
            }
            NetworkResponse.Success(true)
        } catch (e: Exception) {
            NetworkResponse.Failure(e.localizedMessage ?: "Failed to update name")
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}