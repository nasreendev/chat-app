package com.test.baatcheet.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    override suspend fun signUp(
        email: String,
        password: String,
    ): NetworkResponse<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                NetworkResponse.Success(true)
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

    override fun signOut() {
        auth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}