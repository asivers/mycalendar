package com.asivers.mycalendar.data

data class MonthInfo(
    val numberOfDays: Int,
    val dayOfWeekOf1st : Int,
    val holidays: Map<Int, LocalString>,
    val notHolidays: Map<Int, LocalString>,
    val today: Int?
)
