package com.asivers.mycalendar.data.scheme.size

import androidx.compose.ui.unit.TextUnit

data class FontSizeSpScheme(
    val main: TextUnit,
    val dropdownHeader: TextUnit,
    val dropdownItem: TextUnit, // except year dropdown
    val mvHeaderWeek: TextUnit,
    val yvMonthName: TextUnit,
    val yvHeaderWeek: TextUnit,
    val yvDay: TextUnit
)
