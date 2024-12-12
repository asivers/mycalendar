package com.asivers.mycalendar.data

import com.asivers.mycalendar.utils.getCurrentDayOfMonth
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear

data class SelectedDateInfo(
    val year: Int = getCurrentYear(),
    val monthIndex: Int = getCurrentMonthIndex(),
    val dayOfMonth: Int = getCurrentDayOfMonth(),
    val yearOnMonthView: Int = year,
    val byDropdown: Boolean = false
)
