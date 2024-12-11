package com.asivers.mycalendar.utils

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import com.asivers.mycalendar.data.DayInMonthGridInfo
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo

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

fun changeDay(
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    selectedDay: MutableIntState,
    dayInMonthGridInfo: DayInMonthGridInfo
) {
    selectedDay.intValue = dayInMonthGridInfo.dayValue
    if (!dayInMonthGridInfo.inThisMonth) {
        if (dayInMonthGridInfo.dayValue < 15) {
            nextMonth(selectedYearInfo, selectedMonthInfo)
        } else {
            previousMonth(selectedYearInfo, selectedMonthInfo)
        }
    }
}
