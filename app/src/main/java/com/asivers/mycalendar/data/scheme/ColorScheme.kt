package com.asivers.mycalendar.data.scheme

import androidx.compose.ui.graphics.Color

data class ColorScheme(
    val monthViewTop: Color, // month view top gradient, year view selected month
    val viewsBottom: Color, // all views bottom gradient, month dropdown text, current year dropdown text, inactive settings gear
    val yearViewBtnBottom: Color, // YearViewButton bottom gradient
    val lightElement: Color, // year view holidays, settings view elements
    val yearViewTop: Color, // year view and YearViewButton top gradient, month view holidays, not current year dropdown text
    val yearViewMedium: Color, // year view medium gradient
    val settingsViewTop: Color // settings view top gradient
)
