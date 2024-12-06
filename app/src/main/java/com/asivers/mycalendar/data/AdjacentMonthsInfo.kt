package com.asivers.mycalendar.data

data class AdjacentMonthsInfo(
    val prevMonthNumberOfDays: Int,
    val prevMonthHolidaysAndNotHolidays: HolidaysAndNotHolidays,
    val prevMonthToday: Int?,
    val nextMonthHolidaysAndNotHolidays: HolidaysAndNotHolidays,
    val nextMonthToday: Int?
)
