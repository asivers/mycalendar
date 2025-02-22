package com.asivers.mycalendar.data

data class MonthInfo(
    val lengthOfMonth: Int,
    val dayOfWeekOf1st: Int,
    val holidaysAndNotHolidays: HolidaysAndNotHolidays,
    val today: Int?,
    val daysWithNotes: List<Int>,
    val adjacentMonthsInfo: AdjacentMonthsInfo,
    val weekNumbers: List<Int> = listOf()
) {
    constructor(
        baseMonthInfo: BaseMonthInfo,
        daysWithNotes: List<Int>,
        adjacentMonthsInfo: AdjacentMonthsInfo,
        weekNumbers: List<Int> = listOf()
    ): this(
        lengthOfMonth = baseMonthInfo.lengthOfMonth,
        dayOfWeekOf1st = baseMonthInfo.dayOfWeekOf1st,
        holidaysAndNotHolidays = baseMonthInfo.holidaysAndNotHolidays,
        today = baseMonthInfo.today,
        daysWithNotes = daysWithNotes,
        adjacentMonthsInfo = adjacentMonthsInfo,
        weekNumbers = weekNumbers
    )
}
