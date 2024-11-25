package com.asivers.mycalendar.data

data class MonthInfo(
    val numberOfDays: Int,
    val dayOfWeekOf1st : Int,
    val holidays: Map<Int, String>,
    val notHolidays: Map<Int, String>,
    val today: Int?
)
