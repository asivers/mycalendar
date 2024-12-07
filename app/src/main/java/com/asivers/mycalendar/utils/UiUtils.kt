package com.asivers.mycalendar.utils

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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

fun fadeSlow(): ContentTransform {
    return fadeByStiffness(Spring.StiffnessLow)
}

fun fadeFast(): ContentTransform {
    return fadeByStiffness(Spring.StiffnessMediumLow)
}

fun fadeVeryFast(): ContentTransform {
    return fadeByStiffness(Spring.StiffnessHigh)
}

fun slideFromLeftToRight(): ContentTransform {
    return slideInFromLeft() togetherWith slideOutToRight()
}

fun slideFromRightToLeft(): ContentTransform {
    return slideInFromRight() togetherWith slideOutToLeft()
}

fun Modifier.whiteBorder(): Modifier = this.border(
    width = 1.dp,
    color = Color.White
)

private fun fadeByStiffness(stiffness: Float): ContentTransform {
    return fadeIn(spring(stiffness = stiffness)) togetherWith fadeOut(spring(stiffness = stiffness))
}

private fun slideInFromLeft(): EnterTransition {
    return slideInHorizontally(spring(stiffness = Spring.StiffnessLow)) { width -> -width }
}

private fun slideInFromRight(): EnterTransition {
    return slideInHorizontally(spring(stiffness = Spring.StiffnessLow)) { width -> width }
}

private fun slideOutToLeft(): ExitTransition {
    return slideOutHorizontally(spring(stiffness = Spring.StiffnessLow)) { width -> -width }
}

private fun slideOutToRight(): ExitTransition {
    return slideOutHorizontally(spring(stiffness = Spring.StiffnessLow)) { width -> width }
}
