package com.test.baatcheet.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.auth.User
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.data.usecases.GetAllUsersUseCase
import com.test.baatcheet.data.usecases.SignOutUseCase
import com.test.baatcheet.domain.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val users: NetworkResponse<List<UserModel>> = NetworkResponse.Idle,
)

class HomeViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchUsers()
    }

    fun signOut() {
        signOutUseCase.invoke()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    users = NetworkResponse.Loading
                )
            }
            val users = getAllUsersUseCase.invoke()
            _state.update {
                it.copy(
                    users = users
                )
            }
        }
    }
}