package com.example.mycalendar.utils

import androidx.compose.ui.graphics.Color
import com.example.mycalendar.data.HolidaysInfo
import com.example.mycalendar.data.MonthInfo
import com.example.mycalendar.ui.theme.custom.CustomColor
import java.util.Calendar
import java.util.GregorianCalendar

fun getMonthInfo(year: Int, monthIndex: Int, holidaysInfo: HolidaysInfo): MonthInfo {
    val firstOfThisMonth = GregorianCalendar(year, monthIndex, 1)
    val numberOfDays = firstOfThisMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
    val dayOfWeekOf1st = (firstOfThisMonth.get(Calendar.DAY_OF_WEEK) + 5) % 7
    val holidays = mutableSetOf<Int>()
    if (monthIndex in holidaysInfo.holidayDatesEveryYear) {
        holidays.addAll(holidaysInfo.holidayDatesEveryYear[monthIndex]!!)
    }
    if (year in holidaysInfo.holidayDatesOneTime &&
        monthIndex in holidaysInfo.holidayDatesOneTime[year]!!) {
        holidays.addAll(holidaysInfo.holidayDatesOneTime[year]!![monthIndex]!!)
    }
    val today = if (year == getCurrentYear() && monthIndex == getCurrentMonthIndex())
        getCurrentDayOfMonth() else null
    return MonthInfo(numberOfDays, dayOfWeekOf1st, holidays, today)
}

fun getDayValueForMonthTableElement(
    weekIndex: Int,
    dayOfWeekIndex: Int,
    numberOfDaysInMonth: Int,
    dayOfWeekOf1stOfMonth : Int
): Int? {
    val value = weekIndex * 7 + dayOfWeekIndex - dayOfWeekOf1stOfMonth + 1
    return if (value in 1..numberOfDaysInMonth) value else null
}

fun getTextColor(dayValue: Int?, holidays: Set<Int>, dayOfWeekIndex: Int): Color {
    if (dayValue == null) {
        return CustomColor.TRANSPARENT
    }
    if (dayOfWeekIndex > 4 || dayValue in holidays) {
        return CustomColor.MYV_GREEN_DAY_HOLIDAY
    }
    return CustomColor.WHITE
}

fun getCurrentYear() = Calendar.getInstance().get(Calendar.YEAR)
fun getCurrentMonthIndex() = Calendar.getInstance().get(Calendar.MONTH)
fun getCurrentDayOfMonth() = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
