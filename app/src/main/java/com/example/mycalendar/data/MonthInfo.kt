package com.example.mycalendar.data

data class MonthInfo(
    val numberOfDays: Int,
    val dayOfWeekOf1st : Int,
    val holidays: Set<Int>
)
