package com.asivers.mycalendar.ui.theme.custom

import android.content.res.Configuration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val smallFontSizeDpScheme = CustomFontSizeDpScheme(
    main = 24.dp,
    dropdownHeader = 26.dp,
    monthDropdownItem = 20.dp,
    mvHeaderWeek = 12.dp,
    yvMonthName = 14.dp,
    yvHeaderWeek = 7.dp,
    yvDay = 9.dp
)

val bigFontSizeDpScheme = CustomFontSizeDpScheme(
    main = 26.dp,
    dropdownHeader = 28.dp,
    monthDropdownItem = 22.dp,
    mvHeaderWeek = 14.dp,
    yvMonthName = 16.dp,
    yvHeaderWeek = 9.dp,
    yvDay = 11.dp
)

fun getFontSizeDpScheme(config: Configuration): CustomFontSizeDpScheme {
    if (config.screenHeightDp > 900) {
        return bigFontSizeDpScheme
    } else {
        return smallFontSizeDpScheme
    }
}

data class CustomFontSizeDpScheme(
    val main: Dp,
    val dropdownHeader: Dp,
    val monthDropdownItem: Dp,
    val mvHeaderWeek: Dp,
    val yvMonthName: Dp,
    val yvHeaderWeek: Dp,
    val yvDay: Dp
)
