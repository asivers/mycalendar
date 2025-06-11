package com.asivers.mycalendar.data

data class AdjacentMonthsInfo(
    val lengthOfPrevMonth: Int,
    val prevMonthHolidaysAndNotHolidays: HolidaysAndNotHolidays,
    val prevMonthToday: Int?,
    val prevMonthDaysWithNotes: List<Int>,
    val nextMonthHolidaysAndNotHolidays: HolidaysAndNotHolidays,
    val nextMonthToday: Int?,
    val nextMonthDaysWithNotes: List<Int>,
)
