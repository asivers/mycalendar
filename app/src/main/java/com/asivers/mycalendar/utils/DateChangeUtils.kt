package com.asivers.mycalendar.utils

import androidx.compose.runtime.MutableState
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.DisplayedMonth
import java.util.Calendar

fun previousMonth(selectedDateInfo: SelectedDateInfo): SelectedDateInfo {
    val date = selectedDateInfo.getDate()
    date.add(Calendar.MONTH, -1) // now it is previous month

    val updatedYear = date.get(Calendar.YEAR)
    val updatedMonthIndex = date.get(Calendar.MONTH)
    val updatedDayOfMonth = date.get(Calendar.DAY_OF_MONTH)

    if (updatedYear < 1900) {
        return selectedDateInfo
    }
    return SelectedDateInfo(updatedYear, updatedMonthIndex, updatedDayOfMonth)
}

fun nextMonth(selectedDateInfo: SelectedDateInfo): SelectedDateInfo {
    val date = selectedDateInfo.getDate()
    date.add(Calendar.MONTH, 1) // now it is next month

    val updatedYear = date.get(Calendar.YEAR)
    val updatedMonthIndex = date.get(Calendar.MONTH)
    val updatedDayOfMonth = date.get(Calendar.DAY_OF_MONTH)

    if (updatedYear > 2100) {
        return selectedDateInfo
    }
    return SelectedDateInfo(updatedYear, updatedMonthIndex, updatedDayOfMonth)
}

fun previousDay(selectedDateInfo: SelectedDateInfo): SelectedDateInfo {
    val date = selectedDateInfo.getDate()
    date.add(Calendar.DAY_OF_MONTH, -1) // now it is previous day

    val updatedYear = date.get(Calendar.YEAR)
    val updatedMonthIndex = date.get(Calendar.MONTH)
    val updatedDayOfMonth = date.get(Calendar.DAY_OF_MONTH)

    if (updatedYear < 1900) {
        return selectedDateInfo
    }
    return SelectedDateInfo(updatedYear, updatedMonthIndex, updatedDayOfMonth)
}

fun nextDay(selectedDateInfo: SelectedDateInfo): SelectedDateInfo {
    val date = selectedDateInfo.getDate()
    date.add(Calendar.DAY_OF_MONTH, 1) // now it is next day

    val updatedYear = date.get(Calendar.YEAR)
    val updatedMonthIndex = date.get(Calendar.MONTH)
    val updatedDayOfMonth = date.get(Calendar.DAY_OF_MONTH)

    if (updatedYear > 2100) {
        return selectedDateInfo
    }
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
    selectedDateState.value = SelectedDateInfo(
        year = selectedDateState.value.year,
        monthIndex = selectedMonthIndex,
        dayOfMonth = selectedDateState.value.dayOfMonth,
        byDropdown = true
    )
}

fun getOnYearSelected(
    selectedDateState: MutableState<SelectedDateInfo>,
    selectedYear: Int,
    onYearView: Boolean
) {
    selectedDateState.value = SelectedDateInfo(
        year = selectedYear,
        monthIndex = selectedDateState.value.monthIndex,
        dayOfMonth = selectedDateState.value.dayOfMonth,
        yearOnMonthView = if (onYearView) selectedDateState.value.yearOnMonthView else selectedYear,
        byDropdown = true
    )
}
