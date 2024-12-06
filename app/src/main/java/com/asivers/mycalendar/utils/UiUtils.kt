package com.asivers.mycalendar.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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

fun fadeInLow(): EnterTransition {
    return fadeIn(spring(Spring.StiffnessLow))
}

fun fadeOutLow(): ExitTransition {
    return fadeOut(spring(Spring.StiffnessLow))
}

fun slideInFromLeft(): EnterTransition {
    return slideInHorizontally(tween(220)) { width -> -width }
}

fun slideInFromRight(): EnterTransition {
    return slideInHorizontally(tween(220)) { width -> width }
}

fun slideOutToLeft(): ExitTransition {
    return slideOutHorizontally(tween(220)) { width -> -width }
}

fun slideOutToRight(): ExitTransition {
    return slideOutHorizontally(tween(220)) { width -> width }
}

fun Modifier.whiteBorder(): Modifier = this.border(
    width = 1.dp,
    color = Color.White
)
