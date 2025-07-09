package com.test.baatcheet.di

import com.test.baatcheet.data.repository.AuthRepositoryImpl
import com.test.baatcheet.data.usecases.IsUserLoggedInUseCase
import com.test.baatcheet.data.usecases.SignInUseCase
import com.test.baatcheet.data.usecases.SignOutUseCase
import com.test.baatcheet.data.usecases.SignUpUseCase
import com.test.baatcheet.domain.repository.AuthRepository
import com.test.baatcheet.presentation.auth.AuthViewModel
import com.test.baatcheet.presentation.home.HomeViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {


    singleOf(::AuthRepositoryImpl) {
        bind<AuthRepository>()
    }

    singleOf(::IsUserLoggedInUseCase)

    singleOf(::SignUpUseCase)

    singleOf(::SignInUseCase)

    singleOf(::SignOutUseCase)

    viewModelOf(::AuthViewModel)

    viewModelOf(::HomeViewModel)


}