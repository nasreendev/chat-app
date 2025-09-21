package com.test.baatcheet.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.test.baatcheet.presentation.home.HomeScreen
import com.test.baatcheet.presentation.profile.ProfileScreen

@Composable
fun BottomNavHost(
    modifier: Modifier = Modifier,
    selectedItems: Int,
    popBackStack: () -> Unit,
    navToChat: (String, String) -> Unit,
    navToAdd: () -> Unit,
) {
    when (selectedItems) {
        0 -> HomeScreen(
            popBackStack = {
                popBackStack.invoke()
            },
            modifier = modifier,
//            navToChat = { name, id ->
//                navToChat.invoke(name, id)
//            },
            navToAdd = {
                navToAdd.invoke()
            }
        )

        1 -> ProfileScreen()
    }
}