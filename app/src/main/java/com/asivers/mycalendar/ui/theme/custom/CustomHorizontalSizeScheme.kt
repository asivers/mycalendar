package com.asivers.mycalendar.ui.theme.custom

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val smallHorizontalSizeScheme = CustomHorizontalSizeScheme(
    yearDropdown = 70.dp,
    yvMonthPadding = 3.dp
)

val bigHorizontalSizeScheme = CustomHorizontalSizeScheme(
    yearDropdown = 75.dp,
    yvMonthPadding = 5.dp
)

fun getHorizontalSizeScheme(screenWidthDp: Int): CustomHorizontalSizeScheme {
    if (screenWidthDp > 400) {
        return bigHorizontalSizeScheme
    } else {
        return smallHorizontalSizeScheme
    }
}

data class CustomHorizontalSizeScheme(
    val yearDropdown: Dp,
    val yvMonthPadding: Dp
)
