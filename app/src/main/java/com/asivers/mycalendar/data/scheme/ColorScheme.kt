package com.asivers.mycalendar.data.scheme

import androidx.compose.ui.graphics.Color

data class ColorScheme(
    val id: String,
    val text: Color,
    val viewsTop: Color, // all views top gradient, year view selected month
    val viewsBottom: Color, // all views bottom gradient, month dropdown item, current year dropdown item, inactive settings gear
    val yearViewBtnTop: Color, // year view and YearViewButton top gradient, not current year dropdown item
    val yearViewBtnBottom: Color, // year view medium gradient and YearViewButton bottom gradient
    val brightElement: Color, // holidays, active settings elements
    val selectedItemInDropdown: Color = viewsBottom, // month or selected item in dropdown and note text
    val notSelectedYearInDropdown: Color = yearViewBtnTop,
    val notSelectedSettingInDropdown: Color = viewsTop,
    val inactiveSettingsGear: Color = viewsBottom,
    val selectedMonthOnYearView: Color = viewsTop,
    val dropdownBackground: Color = text,
    val inputNoteBackground: Color = text,
    val existingNoteBackground: Color = text,
    val timeSelectorElement: Color = selectedItemInDropdown
)
