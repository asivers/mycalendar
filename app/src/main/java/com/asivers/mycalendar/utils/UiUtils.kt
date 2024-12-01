package com.asivers.mycalendar.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = this.clickable(
    interactionSource = null,
    indication = null
) {
    onClick()
}

fun Modifier.whiteBorder(): Modifier = this.border(
    width = 1.dp,
    color = Color.White
)
