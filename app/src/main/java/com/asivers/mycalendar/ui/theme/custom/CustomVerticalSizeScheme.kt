package com.asivers.mycalendar.ui.theme.custom

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val smallVerticalSizeScheme = CustomVerticalSizeScheme(
    yvMonthPadding = 5.dp
)

val bigVerticalSizeScheme = CustomVerticalSizeScheme(
    yvMonthPadding = 7.dp
)

fun getVerticalSizeScheme(screenHeightDp: Int): CustomVerticalSizeScheme {
    if (screenHeightDp > 900) {
        return bigVerticalSizeScheme
    } else {
        return smallVerticalSizeScheme
    }
}

data class CustomVerticalSizeScheme(
    val yvMonthPadding: Dp
)
