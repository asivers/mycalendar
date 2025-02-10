package com.asivers.mycalendar.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.MutableFloatState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreInterceptKeyBeforeSoftKeyboard
import androidx.compose.ui.input.pointer.pointerInput
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

fun Modifier.onHorizontalSwipe(
    horizontalOffset: MutableFloatState,
    onSwipeToLeft: () -> Unit,
    onSwipeToRight: () -> Unit = onSwipeToLeft,
    minimumSwipe: Float = 50f
): Modifier = this.pointerInput(Unit) {
    detectHorizontalDragGestures(
        onDragStart = {
            horizontalOffset.floatValue = 0f
        },
        onDragEnd = {
            if (horizontalOffset.floatValue > minimumSwipe) {
                onSwipeToRight()
            } else if (horizontalOffset.floatValue < -minimumSwipe) {
                onSwipeToLeft()
            }
        }
    ) { _, dragAmount ->
        horizontalOffset.value += dragAmount
    }
}
