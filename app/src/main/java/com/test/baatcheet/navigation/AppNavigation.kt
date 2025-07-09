package com.test.baatcheet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.baatcheet.presentation.auth.AuthScreen
import com.test.baatcheet.presentation.home.HomeScreen

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
                    navController.navigate(Routes.HomeScreen)
                }
            )
        }
        composable<Routes.HomeScreen> {
            HomeScreen(
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
    }

}