package com.test.baatcheet.data.usecases

import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.model.UserModel
import com.test.baatcheet.domain.repository.UserRepository

class GetAllUsersUseCase(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(): NetworkResponse<List<UserModel>> {
        return repository.getAllUsers()
    }
}