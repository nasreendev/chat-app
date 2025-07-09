package com.test.baatcheet.data.usecases

import com.test.baatcheet.domain.repository.AuthRepository

class IsUserLoggedInUseCase(
    private val repository: AuthRepository,
) {

    operator fun invoke(): Boolean = repository.isUserLoggedIn()
}