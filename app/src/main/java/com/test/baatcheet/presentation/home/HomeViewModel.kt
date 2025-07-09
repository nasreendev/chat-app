package com.test.baatcheet.presentation.home

import androidx.lifecycle.ViewModel
import com.test.baatcheet.data.usecases.SignOutUseCase

class HomeViewModel(
    private val signOutUseCase: SignOutUseCase
): ViewModel() {

    fun signOut() {
        signOutUseCase.invoke()
    }
}