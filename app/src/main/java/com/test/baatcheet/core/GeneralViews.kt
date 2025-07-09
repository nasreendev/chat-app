package com.test.baatcheet.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.test.baatcheet.ui.theme.MainColor

@Composable
fun HorizontalSpace(
    dp: Int = 10,
) {
    Spacer(Modifier.width(dp.dp))
}

@Composable
fun VerticalSpace(
    dp: Int = 10,
) {
    Spacer(Modifier.height(dp.dp))
}

@Composable
fun LoadingBox() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MainColor)
    }
}

@Composable
fun Int.getColor() = colorResource(this)