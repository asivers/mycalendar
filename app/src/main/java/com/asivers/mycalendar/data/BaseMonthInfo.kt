package com.asivers.mycalendar.data

data class BaseMonthInfo(
    val lengthOfMonth: Int,
    val dayOfWeekOf1st: Int,
    val holidaysAndNotHolidays: HolidaysAndNotHolidays,
    val today: Int?
)
