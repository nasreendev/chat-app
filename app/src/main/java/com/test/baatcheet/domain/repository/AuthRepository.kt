package com.test.baatcheet.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.test.baatcheet.data.network.NetworkResponse

interface AuthRepository {
    suspend fun signUp(email: String,password: String): NetworkResponse<Boolean>
    suspend fun signIn(email: String,password: String): NetworkResponse<FirebaseUser>
    fun signOut()
    fun isUserLoggedIn(): Boolean
}