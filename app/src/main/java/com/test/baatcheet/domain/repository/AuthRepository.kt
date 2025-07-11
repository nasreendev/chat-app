package com.test.baatcheet.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.test.baatcheet.data.network.NetworkResponse

interface AuthRepository {
    suspend fun signUp(email: String, password: String): NetworkResponse<FirebaseUser>
    suspend fun signIn(email: String, password: String): NetworkResponse<FirebaseUser>
    suspend fun saveUserInDb(
        uid: String,
        name: String,
        password: String,
        email: String
    ): NetworkResponse<Boolean>
    suspend fun setDisplayName(name: String): NetworkResponse<Boolean>
    fun signOut()
    fun isUserLoggedIn(): Boolean
}