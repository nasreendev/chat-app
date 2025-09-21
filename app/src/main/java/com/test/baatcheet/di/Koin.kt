package com.test.baatcheet.di

import com.test.baatcheet.data.repository.AuthRepositoryImpl
import com.test.baatcheet.data.repository.ChatRepositoryImpl
import com.test.baatcheet.data.repository.UserRepositoryImpl
import com.test.baatcheet.data.usecases.AddFriendUseCase
import com.test.baatcheet.data.usecases.GetAllFriendsUseCase
import com.test.baatcheet.data.usecases.GetAllUsersUseCase
import com.test.baatcheet.data.usecases.GetLoggedInUserUseCase
import com.test.baatcheet.data.usecases.IsUserLoggedInUseCase
import com.test.baatcheet.data.usecases.ListenToMessagesUseCase
import com.test.baatcheet.data.usecases.SendMessageUseCase
import com.test.baatcheet.data.usecases.SetDisplayNameUseCase
import com.test.baatcheet.data.usecases.SignInUseCase
import com.test.baatcheet.data.usecases.SignOutUseCase
import com.test.baatcheet.data.usecases.SignUpUseCase
import com.test.baatcheet.domain.repository.AuthRepository
import com.test.baatcheet.domain.repository.ChatRepository
import com.test.baatcheet.domain.repository.UserRepository
import com.test.baatcheet.presentation.auth.AuthViewModel
import com.test.baatcheet.presentation.chat.ChatViewModel
import com.test.baatcheet.presentation.home.HomeViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {


    singleOf(::AuthRepositoryImpl) {
        bind<AuthRepository>()
    }
    singleOf(::UserRepositoryImpl) {
        bind<UserRepository>()
    }
    singleOf(::ChatRepositoryImpl) {
        bind<ChatRepository>()
    }
    singleOf(::IsUserLoggedInUseCase)

    singleOf(::SignUpUseCase)
    singleOf(::SetDisplayNameUseCase)
    singleOf(::SignInUseCase)
    singleOf(::GetLoggedInUserUseCase)
    singleOf(::SignOutUseCase)
    singleOf(::SendMessageUseCase)
    singleOf(::ListenToMessagesUseCase)
    singleOf(::GetAllUsersUseCase)
    singleOf(::GetAllFriendsUseCase)
    singleOf(::AddFriendUseCase)
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ChatViewModel)


}