package com.example.mycalendar.utils

import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Color
import com.example.mycalendar.data.HolidaysInfo
import com.example.mycalendar.data.MonthInfo
import com.example.mycalendar.ui.theme.CustomColor
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
    return MonthInfo(numberOfDays, dayOfWeekOf1st, holidays)
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

fun getButtonColors(dayValue: Int?): ButtonColors {
    if (dayValue == null) {
        return ButtonColors(Color.Transparent, Color.White, Color.Green, Color.Yellow)
    }
    return ButtonColors(Color.Transparent, Color.White, Color.Green, Color.Yellow)
}

fun getTextColor(dayValue: Int?, holidays: Set<Int>, dayOfWeekIndex: Int): Color {
    if (dayValue == null) {
        return CustomColor.Transparent
    }
    if (dayOfWeekIndex > 4 || dayValue in holidays) {
        return CustomColor.Myv_green_day_holiday
    }
    return CustomColor.White
}
