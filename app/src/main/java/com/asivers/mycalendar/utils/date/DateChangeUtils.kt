package com.asivers.mycalendar.utils.date

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

fun addDays(
    selectedDateInfo: SelectedDateInfo,
    days: Int,
    byDaysLineSlide: Boolean = false
): SelectedDateInfo {
    val oldDate = selectedDateInfo.getDate()
    val newDate = oldDate.plusDays(days.toLong())
    if (newDate.year < 1900 || newDate.year > 2100) {
        return selectedDateInfo
    }
    return SelectedDateInfo(
        year = newDate.year,
        monthValue = newDate.monthValue,
        dayOfMonth = newDate.dayOfMonth,
        byDaysLineSlide = byDaysLineSlide
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
    return SelectedDateInfo(
        year = updatedSelectedDateInfo.year,
        monthValue = updatedSelectedDateInfo.monthValue,
        dayOfMonth = newDayOfMonth
    )
}

fun changeMonth(
    selectedDateInfo: SelectedDateInfo,
    newMonthValue: Int
): SelectedDateInfo {
    val year = selectedDateInfo.year
    val lengthOfSelectedMonth = getLengthOfMonth(year, newMonthValue)
    return SelectedDateInfo(
        year = year,
        monthValue = newMonthValue,
        dayOfMonth = minOf(selectedDateInfo.dayOfMonth, lengthOfSelectedMonth)
    )
}

fun changeYear(
    selectedDateInfo: SelectedDateInfo,
    newYear: Int,
    onYearView: Boolean = false
): SelectedDateInfo {
    val monthValue = selectedDateInfo.monthValue
    val lengthOfSelectedMonth = getLengthOfMonth(newYear, monthValue)
    return SelectedDateInfo(
        year = newYear,
        monthValue = monthValue,
        dayOfMonth = minOf(selectedDateInfo.dayOfMonth, lengthOfSelectedMonth),
        yearOnMonthView = if (onYearView) selectedDateInfo.yearOnMonthView else newYear
    )
}
