package com.test.baatcheet.navigation

import kotlinx.serialization.Serializable

sealed class Routes {


    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object AuthScreen : Routes()

}