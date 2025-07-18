package com.test.baatcheet.domain.repository

import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.domain.model.UserModel

interface UserRepository {
    suspend fun getAllUsers(): NetworkResponse<List<UserModel>>
    suspend fun getLoggedInUser(): NetworkResponse<UserModel>
}