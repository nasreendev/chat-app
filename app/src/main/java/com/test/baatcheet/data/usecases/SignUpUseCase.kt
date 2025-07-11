package com.test.baatcheet.data.usecases

import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SignUpUseCase(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
    ): NetworkResponse<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                val result = repository.signUp(email, password)
                if (result is NetworkResponse.Success) {
                    val user = result.data
                    val job1 = async {
                        repository.saveUserInDb(
                            uid = user.uid,
                            name = name,
                            email = email,
                            password = password
                        )
                    }
                    val job2 = async {
                        repository.setDisplayName(name)
                    }
                    val result1 = job1.await()
                    val result2 = job2.await()
                    NetworkResponse.Success(
                        result1 is NetworkResponse.Success &&
                                result2 is NetworkResponse.Success
                    )
                } else {
                    NetworkResponse.Failure("Auth Not Success")
                }
            }
        } catch (e: Exception) {
            NetworkResponse.Failure(e.localizedMessage ?: "")
        }
    }
}