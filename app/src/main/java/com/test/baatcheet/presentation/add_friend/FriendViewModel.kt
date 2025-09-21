package com.test.baatcheet.presentation.add_friend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.data.usecases.AddFriendUseCase
import com.test.baatcheet.data.usecases.GetAllFriendsUseCase
import com.test.baatcheet.domain.model.UserModel
import com.test.baatcheet.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//
//data class FriendState(
//    val friends: NetworkResponse<List<UserModel>> = NetworkResponse.Idle,
//)
//
//class FriendViewModel(
//    private val addFriendUseCase: AddFriendUseCase,
//) : ViewModel() {
//
//    private val _state = MutableStateFlow(FriendState())
//    val state = _state.asStateFlow()
//
//    private fun addFriend() {
//        viewModelScope.launch {
//            _state.update {
//                it.copy(
//                    friends = NetworkResponse.Loading
//                )
//            }
//            val users = addFriendUseCase.invoke()
//            _state.update {
//                it.copy(
//                    friends = users
//                )
//            }
//        }
//    }
//}