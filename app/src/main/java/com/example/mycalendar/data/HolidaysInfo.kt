package com.example.mycalendar.data

data class HolidaysInfo(
    val holidayDatesEveryYear: Map<Int, List<Int>>,
    val holidayDatesOneTime: Map<Int, Map<Int, List<Int>>>
)
