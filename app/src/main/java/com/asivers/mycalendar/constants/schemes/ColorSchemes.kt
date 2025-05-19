package com.asivers.mycalendar.constants.schemes

import androidx.compose.ui.graphics.Color
import com.asivers.mycalendar.data.scheme.ColorScheme

val SUMMER = ColorScheme(
    text = Color.White,
    viewsTop = Color(0xFF6A89A5),
    viewsBottom = Color(0xFF2F5226),
    yearViewBtnTop = Color(0xFF92D700),
    yearViewBtnBottom = Color(0xFF6DA60F),
    brightElement = Color(0xFFADFF00)
)

val AUTUMN = ColorScheme(
    text = Color.White,
    viewsTop = Color(0xFF53877F),
    viewsBottom = Color(0xFF624823),
    yearViewBtnTop = Color(0xFFFCA600),
    yearViewBtnBottom = Color(0xFFB17811),
    brightElement = Color(0xFFFCEF00)
)

val WINTER = ColorScheme(
    text = Color.White,
    viewsTop = Color(0xFFA45D5D),
    viewsBottom = Color(0xFF285743),
    yearViewBtnTop = Color(0xFF6B8DD5),
    yearViewBtnBottom = Color(0xFF49728B),
    brightElement = Color(0xFF89AEFF)
)

val SPRING = ColorScheme(
    text = Color.White,
    viewsTop = Color(0xFF8A7791),
    viewsBottom = Color(0xFF464E25),
    yearViewBtnTop = Color(0xFFCAD500),
    yearViewBtnBottom = Color(0xFF879113),
    brightElement = Color(0xFFEDFA00)
)

val LIGHT = ColorScheme(
    text = Color(0xFF6A5858),
    viewsTop = Color(0xFFE5EAF2),
    viewsBottom = Color(0xFFE5EAF2),
    yearViewBtnTop = Color(0xFFBCCFEF),
    yearViewBtnBottom = Color(0xFFCFDDF4),
    brightElement = Color(0xFFFF7700),
    selectedItemInDropdown = Color(0xFF6A5858), // text
    notSelectedYearInDropdown = Color(0xFFA5BEE9), // unique
    notSelectedSettingInDropdown = Color(0xFFA5BEE9), // unique
    inactiveSettingsGear = Color(0xFFA5BEE9), // unique
    selectedMonthOnYearView = Color.White, // unique
    dropdownBackground = Color.White, // unique
    inputNoteBackground = Color(0x306A5858), // text with alpha
    existingNoteBackground = Color(0xFFBCCFEF) // yearViewBtnTop
)

val EXPERIMENTAL = ColorScheme(
    text = Color(0xFF4B3C3C),
    viewsTop = Color(0xFFCED28C),
    viewsBottom = Color(0xFFDCDCDC),
    yearViewBtnTop = Color(0xFF2DBD88),
    yearViewBtnBottom = Color(0xFFB6CDC4),
    brightElement = Color(0xFF21966C),
    timeSelectorElement = Color(0xFF4B3C3C) // text
)
