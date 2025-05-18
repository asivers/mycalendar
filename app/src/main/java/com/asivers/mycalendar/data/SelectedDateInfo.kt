package com.asivers.mycalendar.data

import java.time.LocalDate

data class SelectedDateInfo(
    val year: Int,
    val monthValue: Int,
    val dayOfMonth: Int = LocalDate.now().dayOfMonth,
    val yearOnMonthView: Int = year,
    val byMonthSwipe: Boolean = false,
    val refreshFlag: Boolean = false // workaround to refresh state without changing the date
) {

    constructor(localDate: LocalDate): this(
        year = localDate.year,
        monthValue = localDate.monthValue,
        dayOfMonth = localDate.dayOfMonth
    )

    fun getDate(): LocalDate = LocalDate.of(year, monthValue, dayOfMonth)

    fun isToday(): Boolean {
        val today = LocalDate.now()
        return year == today.year
                && monthValue == today.monthValue
                && dayOfMonth == today.dayOfMonth
    }

    fun cloneWithRefresh(): SelectedDateInfo = SelectedDateInfo(
        year = year,
        monthValue = monthValue,
        dayOfMonth = dayOfMonth,
        yearOnMonthView = yearOnMonthView,
        byMonthSwipe = byMonthSwipe,
        refreshFlag = !refreshFlag
    )

}
