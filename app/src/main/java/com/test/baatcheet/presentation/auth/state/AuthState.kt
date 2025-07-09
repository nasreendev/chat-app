package com.test.baatcheet.presentation.auth.state

import com.google.firebase.auth.FirebaseUser
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.presentation.auth.event.AuthType

data class AuthState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val signInResponse: NetworkResponse<FirebaseUser> = NetworkResponse.Idle,
    val signUpResponse: NetworkResponse<Boolean> = NetworkResponse.Idle,
    val authType: AuthType= AuthType.SIGN_UP
)