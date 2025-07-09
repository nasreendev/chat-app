package com.test.baatcheet.data.usecases

import com.google.firebase.auth.FirebaseUser
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.repository.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
    ): NetworkResponse<FirebaseUser> = repository.signIn(email, password)
}