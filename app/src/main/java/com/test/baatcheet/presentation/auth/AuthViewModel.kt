package com.test.baatcheet.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.data.usecases.IsUserLoggedInUseCase
import com.test.baatcheet.data.usecases.SignInUseCase
import com.test.baatcheet.data.usecases.SignOutUseCase
import com.test.baatcheet.data.usecases.SignUpUseCase
import com.test.baatcheet.presentation.auth.event.AuthEvent
import com.test.baatcheet.presentation.auth.event.AuthType
import com.test.baatcheet.presentation.auth.state.AuthState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
) : ViewModel() {


    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _event = Channel<AuthEvent>()
    val event = _event.receiveAsFlow()


    fun setName(name: String) {
        _state.update {
            it.copy(
                name = name
            )
        }
    }

    fun setEmail(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    fun setPassword(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }

    fun switchAuthType() {
        val authType = if (_state.value.authType == AuthType.SIGN_UP) {
            AuthType.SIGN_IN
        } else {
            AuthType.SIGN_UP
        }
        _state.update {
            it.copy(
                authType = authType
            )
        }
    }

    fun signIn() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    signInResponse = NetworkResponse.Loading
                )
            }
            val response = signInUseCase.invoke(
                email = _state.value.email,
                password = _state.value.password
            )
            _state.update {
                it.copy(
                    signInResponse = response
                )
            }
            when (response) {
                is NetworkResponse.Failure -> {
                    _event.send(AuthEvent.ShowToast("Sign in Failure"))

                }

                is NetworkResponse.Success<*> -> {
                    _event.send(AuthEvent.ShowToast("Sign in Success"))
                    _event.send(AuthEvent.NavToHome)
                }

                else -> {

                }
            }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    signUpResponse = NetworkResponse.Loading
                )
            }
            val response = signUpUseCase.invoke(
                email = _state.value.email,
                password = _state.value.password
            )
            _state.update {
                it.copy(
                    signUpResponse = response
                )
            }
            when (response) {
                is NetworkResponse.Failure -> {
                    _event.send(AuthEvent.ShowToast("Sign up Failure"))

                }

                is NetworkResponse.Success<*> -> {
                    _event.send(AuthEvent.ShowToast("Sign up Success"))
                    _event.send(AuthEvent.NavToHome)
                }

                else -> {

                }
            }
        }
    }



    fun checkAuthStatus() {
        if (isUserLoggedInUseCase.invoke()) {
            viewModelScope.launch {
                _event.send(AuthEvent.NavToHome)
            }
        }
    }
}