package com.asivers.mycalendar.utils

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import java.util.Calendar
import java.util.GregorianCalendar

fun previousMonth(
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
) {
    val monthIndexBeforeUpdate = selectedMonthInfo.value.monthIndex
    val yearBeforeUpdate = selectedYearInfo.value.year

    if (monthIndexBeforeUpdate == 0) {
        if (yearBeforeUpdate == 1900) return
        val yearAfterUpdate = yearBeforeUpdate - 1
        selectedMonthInfo.value = SelectedMonthInfo(yearAfterUpdate, 11)
        selectedYearInfo.value = SelectedYearInfo(yearAfterUpdate)
    } else {
        val monthIndexAfterUpdate = monthIndexBeforeUpdate - 1
        selectedMonthInfo.value = SelectedMonthInfo(yearBeforeUpdate, monthIndexAfterUpdate)
    }
}

fun nextMonth(
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
) {
    val monthIndexBeforeUpdate = selectedMonthInfo.value.monthIndex
    val yearBeforeUpdate = selectedYearInfo.value.year

    if (monthIndexBeforeUpdate == 11) {
        if (yearBeforeUpdate == 2100) return
        val yearAfterUpdate = yearBeforeUpdate + 1
        selectedMonthInfo.value = SelectedMonthInfo(yearAfterUpdate, 0)
        selectedYearInfo.value = SelectedYearInfo(yearAfterUpdate)
    } else {
        val monthIndexAfterUpdate = monthIndexBeforeUpdate + 1
        selectedMonthInfo.value = SelectedMonthInfo(yearBeforeUpdate, monthIndexAfterUpdate)
    }
}

fun previousDay(
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    selectedDay: MutableIntState
) {
    val date = GregorianCalendar(
        selectedMonthInfo.value.year,
        selectedMonthInfo.value.monthIndex,
        selectedDay.intValue)
    date.add(Calendar.DAY_OF_MONTH, -1) // now it is previous day
    selectedDay.intValue = date.get(Calendar.DAY_OF_MONTH)
    if (date.get(Calendar.MONTH) != selectedMonthInfo.value.monthIndex) {
        previousMonth(selectedYearInfo, selectedMonthInfo)
    }
}

fun nextDay(
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    selectedDay: MutableIntState
) {
    val date = GregorianCalendar(
        selectedMonthInfo.value.year,
        selectedMonthInfo.value.monthIndex,
        selectedDay.intValue)
    date.add(Calendar.DAY_OF_MONTH, 1) // now it is next day
    selectedDay.intValue = date.get(Calendar.DAY_OF_MONTH)
    if (date.get(Calendar.MONTH) != selectedMonthInfo.value.monthIndex) {
        nextMonth(selectedYearInfo, selectedMonthInfo)
    }
}

fun changeDay(
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    selectedDay: MutableIntState,
    dayValue: Int,
    inThisMonth: Boolean
) {
    selectedDay.intValue = dayValue
    if (!inThisMonth) {
        if (dayValue < 15) {
            nextMonth(selectedYearInfo, selectedMonthInfo)
        } else {
            previousMonth(selectedYearInfo, selectedMonthInfo)
        }
    }
}
