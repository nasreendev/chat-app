package com.test.baatcheet.core

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.test.baatcheet.R

@Composable
fun TextView(
    value: String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: Int = 18,
    color: Color = R.color.black.getColor(),
    modifier: Modifier = Modifier,
) {
    Text(
        text = value,
        fontWeight = fontWeight,
        fontSize = fontSize.sp,
        color = color,
        modifier = modifier
    )
}