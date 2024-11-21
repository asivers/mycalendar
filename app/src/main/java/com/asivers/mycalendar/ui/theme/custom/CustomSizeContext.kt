package com.asivers.mycalendar.ui.theme.custom

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val smallSizeContext = CustomSizeContext(
    font = CustomSizeContext.CustomFontSizeContext(
        main = 24.dp,
        dropdownHeader = 26.dp,
        monthDropdownItem = 20.dp,
        mvHeaderWeek = 12.dp,
        yvMonthName = 14.dp,
        yvHeaderWeek = 7.dp,
        yvDay = 9.dp
    ),
    yearDropdownWidth = 70.dp,
    yvMonthHorizontalPadding = 3.dp,
    yvMonthVerticalPadding = 5.dp
)

val bigSizeContext = CustomSizeContext(
    font = CustomSizeContext.CustomFontSizeContext(
        main = 26.dp,
        dropdownHeader = 28.dp,
        monthDropdownItem = 22.dp,
        mvHeaderWeek = 14.dp,
        yvMonthName = 16.dp,
        yvHeaderWeek = 9.dp,
        yvDay = 11.dp
    ),
    yearDropdownWidth = 75.dp,
    yvMonthHorizontalPadding = 5.dp,
    yvMonthVerticalPadding = 7.dp
)

data class CustomSizeContext(
    val font: CustomFontSizeContext,
    val yearDropdownWidth: Dp,
    val yvMonthHorizontalPadding: Dp,
    val yvMonthVerticalPadding: Dp
) {
    data class CustomFontSizeContext(
        val main: Dp,
        val dropdownHeader: Dp,
        val monthDropdownItem: Dp,
        val mvHeaderWeek: Dp,
        val yvMonthName: Dp,
        val yvHeaderWeek: Dp,
        val yvDay: Dp
    )
}
