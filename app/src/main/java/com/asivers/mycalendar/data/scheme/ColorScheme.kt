package com.asivers.mycalendar.data.scheme

import androidx.compose.ui.graphics.Color

data class ColorScheme(
    val text: Color = Color.White,
    val monthViewTop: Color, // month view top gradient, year view selected month
    val viewsBottom: Color, // all views bottom gradient, month dropdown item, current year dropdown item, inactive settings gear
    val yearViewBtnTop: Color, // year view and YearViewButton top gradient, not current year dropdown item
    val yearViewBtnBottom: Color, // year view medium gradient and YearViewButton bottom gradient
    val brightElement: Color, // holidays, active settings elements
    val settingsViewTop: Color, // settings view top gradient
    val monthOrSelectedItemInDropdown: Color = viewsBottom,
    val notSelectedYearInDropdown: Color = yearViewBtnTop,
    val notSelectedSettingInDropdown: Color = settingsViewTop,
    val dropdownBackground: Color? = null
)
