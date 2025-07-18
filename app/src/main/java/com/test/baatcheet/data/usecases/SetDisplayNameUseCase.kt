package com.test.baatcheet.data.usecases

import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.repository.AuthRepository

class SetDisplayNameUseCase(
    private val repository: AuthRepository
) {

    suspend fun invoke(name: String):NetworkResponse<Boolean>{
        return repository.setDisplayName(name)
    }
}