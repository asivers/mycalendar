package com.asivers.mycalendar.utils.date

import com.asivers.mycalendar.data.BaseMonthInfo
import com.asivers.mycalendar.data.DayInfo
import com.asivers.mycalendar.data.HolidaysAndNotHolidays
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.WeekendMode

fun getDayInfoForYearView(
    dayValueRaw: Int, // including values outside of this month: -1, 0, 1, 2, ..., 30, 31, 32, 33 and so on
    baseMonthInfo: BaseMonthInfo,
    weekendMode: WeekendMode
): DayInfo {
    val inMonth = getDisplayedMonth(dayValueRaw, baseMonthInfo.lengthOfMonth)
    if (inMonth != DisplayedMonth.THIS) {
        return DayInfo(inMonth)
    }
    val isToday = dayValueRaw == baseMonthInfo.today
    val dayOfWeekValue = ((dayValueRaw + baseMonthInfo.dayOfWeekOf1st + 5) % 7) + 1
    val holidaysAndNotHolidays = baseMonthInfo.holidaysAndNotHolidays
    val isWeekend = isWeekend(dayValueRaw, dayOfWeekValue, weekendMode, holidaysAndNotHolidays)
    val isHoliday = isHoliday(dayValueRaw, holidaysAndNotHolidays)

    return DayInfo(inMonth, dayValueRaw, isToday, dayOfWeekValue, isWeekend, isHoliday)
}

fun getDayInfo(
    dayValueRaw: Int, // including values outside of this month: -1, 0, 1, 2, ..., 30, 31, 32, 33 and so on
    monthInfo: MonthInfo,
    weekendMode: WeekendMode
): DayInfo {
    val inMonth = getDisplayedMonth(dayValueRaw, monthInfo.lengthOfMonth)

    val dayValue: Int
    val holidaysAndNotHolidays: HolidaysAndNotHolidays
    val today: Int?
    val daysWithNotes: List<Int>

    when (inMonth) {
        DisplayedMonth.THIS -> {
            dayValue = dayValueRaw
            holidaysAndNotHolidays = monthInfo.holidaysAndNotHolidays
            today = monthInfo.today
            daysWithNotes = monthInfo.daysWithNotes
        }
        DisplayedMonth.PREVIOUS -> {
            dayValue = dayValueRaw + monthInfo.adjacentMonthsInfo.prevMonthNumberOfDays
            holidaysAndNotHolidays = monthInfo.adjacentMonthsInfo.prevMonthHolidaysAndNotHolidays
            today = monthInfo.adjacentMonthsInfo.prevMonthToday
            daysWithNotes = monthInfo.adjacentMonthsInfo.prevMonthDaysWithNotes
        }
        DisplayedMonth.NEXT -> {
            dayValue = dayValueRaw - monthInfo.lengthOfMonth
            holidaysAndNotHolidays = monthInfo.adjacentMonthsInfo.nextMonthHolidaysAndNotHolidays
            today = monthInfo.adjacentMonthsInfo.nextMonthToday
            daysWithNotes = monthInfo.adjacentMonthsInfo.nextMonthDaysWithNotes
        }
    }

    val isToday = dayValue == today
    val dayOfWeekValue = ((dayValueRaw + monthInfo.dayOfWeekOf1st + 5) % 7) + 1
    val isWeekend = isWeekend(dayValue, dayOfWeekValue, weekendMode, holidaysAndNotHolidays)
    val isHoliday = isHoliday(dayValue, holidaysAndNotHolidays)
    val isWithNote = dayValue in daysWithNotes

    return DayInfo(inMonth, dayValue, isToday, dayOfWeekValue, isWeekend, isHoliday, isWithNote)
}

private fun getDisplayedMonth(dayValueRaw: Int, lengthOfMonth: Int): DisplayedMonth {
    return if (dayValueRaw <= 0)
        DisplayedMonth.PREVIOUS
    else if (dayValueRaw > lengthOfMonth)
        DisplayedMonth.NEXT
    else
        DisplayedMonth.THIS
}

private fun isWeekend(
    dayValue: Int,
    dayOfWeekValue: Int,
    weekendMode: WeekendMode,
    holidaysAndNotHolidays: HolidaysAndNotHolidays
): Boolean {
    if (weekendMode == WeekendMode.NO_DISPLAY || dayValue in holidaysAndNotHolidays.notHolidays) {
        return false
    }
    if (weekendMode == WeekendMode.ONLY_SUNDAY) {
        return dayOfWeekValue == 7
    }
    return dayOfWeekValue >= 6
}

private fun isHoliday(dayValue: Int, holidaysAndNotHolidays: HolidaysAndNotHolidays): Boolean {
    return dayValue !in holidaysAndNotHolidays.notHolidays &&
            dayValue in holidaysAndNotHolidays.holidays
}
