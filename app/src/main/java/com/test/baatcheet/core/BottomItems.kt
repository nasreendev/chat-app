package com.test.baatcheet.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomItems(
    val title: String,
    val icon: ImageVector,
) {
    Home("Home", Icons.Default.Home),
    Profile("Profile", Icons.Default.Person)
}