package com.asivers.mycalendar.data

data class MonthInfo(
    val numberOfDays: Int,
    val dayOfWeekOf1st : Int,
    val holidaysAndNotHolidays: HolidaysAndNotHolidays,
    val today: Int?,
    val adjacentMonthsInfo: AdjacentMonthsInfo? = null
)
