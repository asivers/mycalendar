package com.asivers.mycalendar.data

data class SelectedMonthInfo(
    var year: Int, // last selected year from month view
    val monthIndex: Int,
    val byDropdown: Boolean = false,
)
