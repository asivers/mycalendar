package com.asivers.mycalendar.data

data class SelectedYearInfo(
    val year: Int,
    val lastSelectedYearFromMonthView: Int = year,
    val byDropdown: Boolean = false
)
