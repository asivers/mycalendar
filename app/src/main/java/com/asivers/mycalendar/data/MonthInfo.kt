package com.asivers.mycalendar.data

data class MonthInfo(
    val numberOfDays: Int,
    val dayOfWeekOf1st : Int,
    val holidays: Map<Int, DayInfo>,
    val notHolidays: Map<Int, DayInfo>,
    val today: Int?
)
