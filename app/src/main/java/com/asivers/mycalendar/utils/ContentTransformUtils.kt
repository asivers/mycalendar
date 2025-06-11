package com.asivers.mycalendar.utils

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown

fun noTransform(): ContentTransform {
    return ContentTransform(EnterTransition.None, ExitTransition.None)
}

fun fadeSlow(): ContentTransform {
    return fadeByStiffness(Spring.StiffnessLow)
}

fun fadeNormal(): ContentTransform {
    return fadeByStiffness(Spring.StiffnessMediumLow)
}

fun fadeFast(): ContentTransform {
    return fadeByStiffness(Spring.StiffnessHigh)
}

fun slideFromLeftToRight(): ContentTransform {
    return slideInFromLeft() togetherWith slideOutToRight()
}

fun slideFromRightToLeft(): ContentTransform {
    return slideInFromRight() togetherWith slideOutToLeft()
}

fun slideWeek(days: Int): ContentTransform {
    val stiffness = if (days == 1) Spring.StiffnessMediumLow else Spring.StiffnessMedium
    val enterTransition = slideInHorizontally(spring(stiffness = stiffness)) { days * it / 7 }
    val exitTransition = fadeOut(tween(durationMillis = 0))
    return enterTransition togetherWith exitTransition
}

fun animateHeaderBackButtonOnViewChange(
    targetState: ViewShownInfo,
    initialState: ViewShownInfo,
): ContentTransform {
    return if (targetState.current == ViewShown.MONTH && initialState.current == ViewShown.MONTH)
        fadeNormal()
    else
        noTransform()
}

fun animateHeaderGearOnViewChange(
    targetState: ViewShownInfo,
    initialState: ViewShownInfo,
): ContentTransform {
    return if (targetState.current == ViewShown.SETTINGS || initialState.current == ViewShown.SETTINGS)
        fadeNormal()
    else
        noTransform()
}

fun animateContentOnViewChange(
    targetState: ViewShownInfo,
    initialState: ViewShownInfo,
): ContentTransform {
    return if (targetState.current == ViewShown.SETTINGS)
        slideFromLeftToRight()
    else if (initialState.current == ViewShown.SETTINGS)
        slideFromRightToLeft()
    else if (targetState.current == ViewShown.YEAR && !targetState.yearViewWasShown)
        fadeFast()
    else
        fadeNormal()
}

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
