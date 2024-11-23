package com.asivers.mycalendar.ui.theme.custom

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit

fun getFontSizeSpScheme(dpScheme: CustomFontSizeDpScheme, density: Density): CustomFontSizeSpScheme {
    return CustomFontSizeSpScheme(
        main = with(density) { dpScheme.main.toSp() },
        dropdownHeader = with(density) { dpScheme.dropdownHeader.toSp() },
        monthDropdownItem = with(density) { dpScheme.monthDropdownItem.toSp() },
        mvHeaderWeek = with(density) { dpScheme.mvHeaderWeek.toSp() },
        yvMonthName = with(density) { dpScheme.yvMonthName.toSp() },
        yvHeaderWeek = with(density) { dpScheme.yvHeaderWeek.toSp() },
        yvDay = with(density) { dpScheme.yvDay.toSp() }
    )
}

data class CustomFontSizeSpScheme(
    val main: TextUnit,
    val dropdownHeader: TextUnit,
    val monthDropdownItem: TextUnit,
    val mvHeaderWeek: TextUnit,
    val yvMonthName: TextUnit,
    val yvHeaderWeek: TextUnit,
    val yvDay: TextUnit
)
