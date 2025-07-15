package com.test.baatcheet.core


fun getValidationError(
    email: String,
    password: String,
): String? {
    return when {
        email.isBlank() && password.isBlank() -> "Email and password cannot be empty"
        email.isBlank() -> "Please enter your email"
        password.isBlank() -> "Please enter your password"
        password.length < 6 -> "Password must be at least 6 Character"
        else -> null
    }
}