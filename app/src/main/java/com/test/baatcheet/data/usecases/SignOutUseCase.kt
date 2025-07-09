package com.test.baatcheet.data.usecases

import com.test.baatcheet.domain.repository.AuthRepository

class SignOutUseCase(
    private val repository: AuthRepository,
) {
    operator fun invoke() {
        repository.signOut()
    }
}