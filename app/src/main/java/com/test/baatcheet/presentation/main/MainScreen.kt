package com.test.baatcheet.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.test.baatcheet.core.BottomItems
import com.test.baatcheet.core.BottomNavHost
import com.test.baatcheet.core.TextView
import com.test.baatcheet.ui.theme.LightBlue
import com.test.baatcheet.ui.theme.MainColor


@Composable
fun MainScreen(
    popBackStack: () -> Unit,
    navToChat: () -> Unit,
) {


    var selectedItem by remember {
        mutableIntStateOf(0)
    }


    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                BottomItems.entries.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                        },
                        label = {
                            TextView(
                                value = item.title,
                                color = if (selectedItem == index) MainColor else LightBlue
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "",
                                tint = if (selectedItem == index) MainColor else LightBlue
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) {
        BottomNavHost(
            modifier = Modifier
                .padding(it),
            selectedItems = selectedItem,
            popBackStack = {
                popBackStack.invoke()
            },
            navToChat = {
                navToChat.invoke()
            }
        )
    }
}