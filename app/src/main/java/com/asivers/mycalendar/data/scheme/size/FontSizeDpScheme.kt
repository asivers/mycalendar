package com.asivers.mycalendar.data.scheme.size

import androidx.compose.ui.unit.Dp

data class FontSizeDpScheme(
    val main: Dp,
    val dropdownHeader: Dp,
    val dropdownItem: Dp, // except year dropdown
    val mvHeaderWeek: Dp,
    val yvMonthName: Dp,
    val yvHeaderWeek: Dp,
    val yvDay: Dp
)
