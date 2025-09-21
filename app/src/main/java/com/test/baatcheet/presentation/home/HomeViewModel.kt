package com.test.baatcheet.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.auth.User
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.data.usecases.AddFriendUseCase
import com.test.baatcheet.data.usecases.GetAllFriendsUseCase
import com.test.baatcheet.data.usecases.GetAllUsersUseCase
import com.test.baatcheet.data.usecases.GetLoggedInUserUseCase
import com.test.baatcheet.data.usecases.SetDisplayNameUseCase
import com.test.baatcheet.data.usecases.SignOutUseCase
import com.test.baatcheet.domain.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HomeState(
    val users: NetworkResponse<List<UserModel>> = NetworkResponse.Idle,
    val loggedInUser: NetworkResponse<UserModel> = NetworkResponse.Idle,
    val name: String = "",
    val friends: NetworkResponse<List<UserModel>> = NetworkResponse.Idle,
    )

class HomeViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase,
    private val setDisplayNameUseCase: SetDisplayNameUseCase,
    private val getAllFriendsUseCase: GetAllFriendsUseCase,
    private val addFriendUseCase: AddFriendUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchUsers()
        fetchFriends()
        fetchLoggedInUser()
    }


    fun setName(name: String) {
        _state.update {
            it.copy(
                name = name
            )
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            setDisplayNameUseCase.invoke(name)
            fetchLoggedInUser()
        }
    }

    fun signOut() {
        signOutUseCase.invoke()
    }

    private fun fetchLoggedInUser() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loggedInUser = NetworkResponse.Loading
                )
            }
            val currentUser = getLoggedInUserUseCase.invoke()
            _state.update {
                it.copy(
                    loggedInUser = currentUser
                )
            }
        }
    }


    private fun fetchFriends() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    friends = NetworkResponse.Loading
                )
            }
            val users = getAllFriendsUseCase.invoke()
            _state.update {
                it.copy(
                    friends = users
                )
            }
        }
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
     fun addFriend(friendId: String) {
        viewModelScope.launch {
          addFriendUseCase.invoke(friendId)
            fetchFriends()
        }
    }
}