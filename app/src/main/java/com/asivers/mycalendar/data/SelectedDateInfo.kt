package com.asivers.mycalendar.data

import java.time.LocalDate

data class SelectedDateInfo(
    val year: Int = LocalDate.now().year,
    val monthValue: Int = LocalDate.now().monthValue,
    val dayOfMonth: Int = LocalDate.now().dayOfMonth,
    val yearOnMonthView: Int = year,
    val byMonthSwipe: Boolean = false,
    val refreshFlag: Boolean = false // workaround to refresh state without changing the date
) {

    fun getDate(): LocalDate = LocalDate.of(year, monthValue, dayOfMonth)

    fun cloneWithRefresh(): SelectedDateInfo = SelectedDateInfo(
        year = year,
        monthValue = monthValue,
        dayOfMonth = dayOfMonth,
        yearOnMonthView = yearOnMonthView,
        byMonthSwipe = byMonthSwipe,
        refreshFlag = !refreshFlag
    )

}
