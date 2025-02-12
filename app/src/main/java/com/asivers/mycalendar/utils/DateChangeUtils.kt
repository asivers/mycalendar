package com.asivers.mycalendar.utils

import androidx.compose.runtime.MutableState
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.DisplayedMonth
import java.util.Calendar
import java.util.GregorianCalendar

fun previousMonth(
    selectedDateInfo: SelectedDateInfo,
    byMonthSwipe: Boolean = false
): SelectedDateInfo {
    val date = selectedDateInfo.getDate()
    date.add(Calendar.MONTH, -1) // now it is previous month

    val updatedYear = date.get(Calendar.YEAR)
    if (updatedYear < 1900) {
        return selectedDateInfo
    }
    val updatedMonthIndex = date.get(Calendar.MONTH)
    val updatedDayOfMonth = date.get(Calendar.DAY_OF_MONTH)

    return SelectedDateInfo(
        year = updatedYear,
        monthIndex = updatedMonthIndex,
        dayOfMonth = updatedDayOfMonth,
        byMonthSwipe = byMonthSwipe
    )
}

fun nextMonth(
    selectedDateInfo: SelectedDateInfo,
    byMonthSwipe: Boolean = false
): SelectedDateInfo {
    val date = selectedDateInfo.getDate()
    date.add(Calendar.MONTH, 1) // now it is next month

    val updatedYear = date.get(Calendar.YEAR)
    if (updatedYear > 2100) {
        return selectedDateInfo
    }
    val updatedMonthIndex = date.get(Calendar.MONTH)
    val updatedDayOfMonth = date.get(Calendar.DAY_OF_MONTH)

    return SelectedDateInfo(
        year = updatedYear,
        monthIndex = updatedMonthIndex,
        dayOfMonth = updatedDayOfMonth,
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
    val date = selectedDateInfo.getDate()
    date.add(Calendar.DAY_OF_MONTH, days) // now it is changed

    val updatedYear = date.get(Calendar.YEAR)
    if (updatedYear < 1900 || updatedYear > 2100) {
        return selectedDateInfo
    }
    val updatedMonthIndex = date.get(Calendar.MONTH)
    val updatedDayOfMonth = date.get(Calendar.DAY_OF_MONTH)

    return SelectedDateInfo(updatedYear, updatedMonthIndex, updatedDayOfMonth)
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
    return SelectedDateInfo(selectedDateInfo.year, selectedDateInfo.monthIndex, newDayOfMonth)
}

fun getOnMonthSelected(
    selectedDateState: MutableState<SelectedDateInfo>,
    selectedMonthIndex: Int
) {
    val year = selectedDateState.value.year
    val maxNumberOfDaysInSelectedMonth = GregorianCalendar(year, selectedMonthIndex, 1)
        .getActualMaximum(Calendar.DAY_OF_MONTH)
    val oldDayOfMonth = selectedDateState.value.dayOfMonth
    val newDayOfMonth = minOf(oldDayOfMonth, maxNumberOfDaysInSelectedMonth)
    selectedDateState.value = SelectedDateInfo(
        year = year,
        monthIndex = selectedMonthIndex,
        dayOfMonth = newDayOfMonth
    )
}

fun getOnYearSelected(
    selectedDateState: MutableState<SelectedDateInfo>,
    selectedYear: Int,
    onYearView: Boolean
) {
    val monthIndex = selectedDateState.value.monthIndex
    val maxNumberOfDaysInSelectedMonth = GregorianCalendar(selectedYear, monthIndex, 1)
        .getActualMaximum(Calendar.DAY_OF_MONTH)
    val oldDayOfMonth = selectedDateState.value.dayOfMonth
    val newDayOfMonth = minOf(oldDayOfMonth, maxNumberOfDaysInSelectedMonth)
    selectedDateState.value = SelectedDateInfo(
        year = selectedYear,
        monthIndex = monthIndex,
        dayOfMonth = newDayOfMonth,
        yearOnMonthView = if (onYearView) selectedDateState.value.yearOnMonthView else selectedYear
    )
}
