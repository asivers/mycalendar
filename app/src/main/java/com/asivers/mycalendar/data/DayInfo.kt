package com.asivers.mycalendar.data

import com.asivers.mycalendar.enums.DisplayedMonth

data class DayInfo(
    val dayValue: Int,
    val inMonth: DisplayedMonth,
    val isToday: Boolean = false,
    val isWeekend: Boolean = false,
    val isHoliday: Boolean = false
)
