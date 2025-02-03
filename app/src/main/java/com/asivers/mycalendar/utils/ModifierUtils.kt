package com.asivers.mycalendar.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreInterceptKeyBeforeSoftKeyboard
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

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onHideKeyboard(onHide: () -> Unit): Modifier = this.onPreInterceptKeyBeforeSoftKeyboard {
    if (it.key == Key.Back) {
        onHide()
    }
    true
}
