package com.test.baatcheet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.test.baatcheet.presentation.auth.AuthScreen
import com.test.baatcheet.presentation.chat.ChatScreen
import com.test.baatcheet.presentation.home.HomeScreen
import com.test.baatcheet.presentation.main.MainScreen
import com.test.baatcheet.presentation.profile.ProfileScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.AuthScreen
    ) {
        composable<Routes.AuthScreen> {
            AuthScreen(
                navToHome = {
                    navController.navigate(Routes.MainScreen)
                }
            )
        }
        composable<Routes.MainScreen> {
            MainScreen(
                popBackStack = {
                    navController.popBackStack()
                },
                navToChat = { name, id ->
                    navController.navigate(Routes.ChatScreen(name, id))
                }
            )
        }
        composable<Routes.ChatScreen> {
            val model = it.toRoute<Routes.ChatScreen>()
            ChatScreen(
                name =model.name,
                id = model.id,
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable<Routes.HomeScreen> {
            HomeScreen(
                popBackStack = {
                    navController.popBackStack()
                },
                navToChat = {name,id->
                    navController.navigate(Routes.ChatScreen(name,id))
                }
            )
        }
    }
}