package com.test.baatcheet.data.network

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class Failure(val error: String) : NetworkResponse<Nothing>()
    data object Loading : NetworkResponse<Nothing>()
    data object Idle : NetworkResponse<Nothing>()
}