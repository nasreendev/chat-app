package com.test.baatcheet.presentation.auth.event

sealed class AuthEvent {
    data class ShowToast(val msg: String): AuthEvent()
    data object NavToHome: AuthEvent()
}