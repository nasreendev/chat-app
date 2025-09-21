package com.test.baatcheet.navigation

import kotlinx.serialization.Serializable

sealed class Routes {


    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object AuthScreen : Routes()

    @Serializable
    data class ChatScreen(val name: String,val id: String) : Routes()

    @Serializable
    data object MainScreen : Routes()

    @Serializable
    data object ProfileScreen : Routes()

    @Serializable
    data object AddFriendScreen : Routes()

}