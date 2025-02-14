package com.asivers.mycalendar.utils

import androidx.compose.runtime.MutableState
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.DisplayedMonth

fun previousMonth(
    selectedDateInfo: SelectedDateInfo,
    byMonthSwipe: Boolean = false
): SelectedDateInfo {
    val oldDate = selectedDateInfo.getDate()
    val newDate = oldDate.minusMonths(1)
    if (newDate.year < 1900) {
        return selectedDateInfo
    }
    return SelectedDateInfo(
        year = newDate.year,
        monthValue = newDate.monthValue,
        dayOfMonth = newDate.dayOfMonth,
        byMonthSwipe = byMonthSwipe
    )
}

fun nextMonth(
    selectedDateInfo: SelectedDateInfo,
    byMonthSwipe: Boolean = false
): SelectedDateInfo {
    val oldDate = selectedDateInfo.getDate()
    val newDate = oldDate.plusMonths(1)
    if (newDate.year > 2100) {
        return selectedDateInfo
    }
    return SelectedDateInfo(
        year = newDate.year,
        monthValue = newDate.monthValue,
        dayOfMonth = newDate.dayOfMonth,
        byMonthSwipe = byMonthSwipe
    )
}

fun previousDay(selectedDateInfo: SelectedDateInfo): SelectedDateInfo {
    return addDays(selectedDateInfo, -1)
}

fun nextDay(selectedDateInfo: SelectedDateInfo): SelectedDateInfo {
    return addDays(selectedDateInfo, 1)
}

fun addDays(selectedDateInfo: SelectedDateInfo, days: Int): SelectedDateInfo {
    val oldDate = selectedDateInfo.getDate()
    val newDate = oldDate.plusDays(days.toLong())
    if (newDate.year < 1900 || newDate.year > 2100) {
        return selectedDateInfo
    }
    return SelectedDateInfo(
        year = newDate.year,
        monthValue = newDate.monthValue,
        dayOfMonth = newDate.dayOfMonth
    )
}

fun changeDay(
    selectedDateInfo: SelectedDateInfo,
    newDayOfMonth: Int,
    inMonth: DisplayedMonth
): SelectedDateInfo {
    val updatedSelectedDateInfo = when (inMonth) {
        DisplayedMonth.PREVIOUS -> previousMonth(selectedDateInfo)
        DisplayedMonth.THIS -> selectedDateInfo
        DisplayedMonth.NEXT -> nextMonth(selectedDateInfo)
    }
    return updateOnlyDayOfMonth(updatedSelectedDateInfo, newDayOfMonth)
}

private fun updateOnlyDayOfMonth(
    selectedDateInfo: SelectedDateInfo,
    newDayOfMonth: Int
): SelectedDateInfo {
    return SelectedDateInfo(selectedDateInfo.year, selectedDateInfo.monthValue, newDayOfMonth)
}

fun getOnMonthSelected(
    selectedDateState: MutableState<SelectedDateInfo>,
    selectedMonthValue: Int
) {
    val selectedDateInfo = selectedDateState.value
    val year = selectedDateInfo.year
    val lengthOfSelectedMonth = getMonthLength(year, selectedMonthValue)
    selectedDateState.value = SelectedDateInfo(
        year = year,
        monthValue = selectedMonthValue,
        dayOfMonth = minOf(selectedDateInfo.dayOfMonth, lengthOfSelectedMonth)
    )
}

fun getOnYearSelected(
    selectedDateState: MutableState<SelectedDateInfo>,
    selectedYear: Int,
    onYearView: Boolean
) {
    val selectedDateInfo = selectedDateState.value
    val monthValue = selectedDateInfo.monthValue
    val lengthOfSelectedMonth = getMonthLength(selectedYear, monthValue)
    selectedDateState.value = SelectedDateInfo(
        year = selectedYear,
        monthValue = monthValue,
        dayOfMonth = minOf(selectedDateInfo.dayOfMonth, lengthOfSelectedMonth),
        yearOnMonthView = if (onYearView) selectedDateState.value.yearOnMonthView else selectedYear
    )
}
