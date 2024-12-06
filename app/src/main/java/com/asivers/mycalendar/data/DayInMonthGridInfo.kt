package com.asivers.mycalendar.data

data class DayInMonthGridInfo(
    val dayValue: Int,
    val inThisMonth: Boolean,
    val isToday: Boolean = false,
    val isWeekend: Boolean = false,
    val isHoliday: Boolean = false
)
