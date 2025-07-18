package com.test.baatcheet.data.usecases

import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.model.UserModel
import com.test.baatcheet.domain.repository.UserRepository

class GetLoggedInUserUseCase(
    private val repository: UserRepository,
) {

    suspend fun invoke(): NetworkResponse<UserModel> {
        return repository.getLoggedInUser()
    }
}