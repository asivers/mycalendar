package com.asivers.mycalendar.data

data class HolidaysAndNotHolidays(
    val holidays: Map<Int, HolidayInfo>,
    val notHolidays: Map<Int, HolidayInfo>,
)
