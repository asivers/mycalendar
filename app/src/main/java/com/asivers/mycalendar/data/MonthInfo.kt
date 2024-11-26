package com.asivers.mycalendar.data

data class MonthInfo(
    val numberOfDays: Int,
    val dayOfWeekOf1st : Int,
    val holidays: Map<Int, HolidayInfo>,
    val notHolidays: Map<Int, HolidayInfo>,
    val today: Int?
)
