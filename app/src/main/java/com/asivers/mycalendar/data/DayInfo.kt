package com.asivers.mycalendar.data

import com.asivers.mycalendar.enums.DisplayedMonth

data class DayInfo(
    val inMonth: DisplayedMonth,
    val dayValue: Int = -1,
    val isToday: Boolean = false,
    val dayOfWeekValue: Int = -1,
    val isWeekend: Boolean = false,
    val isHoliday: Boolean = false,
    val isWithNote: Boolean = false
)
