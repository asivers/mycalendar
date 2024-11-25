package com.asivers.mycalendar.utils

import com.asivers.mycalendar.data.HolidaysForCountry
import com.asivers.mycalendar.data.LocalString
import com.asivers.mycalendar.data.MonthInfo
import java.util.Calendar
import java.util.GregorianCalendar

fun getMonthInfo(year: Int, monthIndex: Int, holidaysForCountry: HolidaysForCountry): MonthInfo {
    val firstOfThisMonth = GregorianCalendar(year, monthIndex, 1)
    val numberOfDays = firstOfThisMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
    val dayOfWeekOf1st = (firstOfThisMonth.get(Calendar.DAY_OF_WEEK) + 5) % 7

    val monthIndexFrom1To12 = monthIndex + 1
    val holidays = mutableMapOf<Int, LocalString>()
    val notHolidays = mutableMapOf<Int, LocalString>()
    if (monthIndexFrom1To12 in holidaysForCountry.everyYear) {
        holidaysForCountry.everyYear[monthIndexFrom1To12]!!.forEach {
            if (it.value.holiday != null) holidays[it.key] = it.value.holiday!!
            else if (it.value.notHoliday != null) notHolidays[it.key] = it.value.notHoliday!!
        }
    }
    if (year in holidaysForCountry.oneTime && monthIndexFrom1To12 in holidaysForCountry.oneTime[year]!!) {
        holidaysForCountry.oneTime[year]!![monthIndexFrom1To12]!!.forEach {
            if (it.value.holiday != null) holidays[it.key] = it.value.holiday!!
            else if (it.value.notHoliday != null) notHolidays[it.key] = it.value.notHoliday!!
        }
    }

    val today = getTodayValue(year, monthIndex)
    return MonthInfo(numberOfDays, dayOfWeekOf1st, holidays, notHolidays, today)
}

private fun getTodayValue(year: Int, monthIndex: Int): Int? {
    val isCurrentMonth = year == getCurrentYear() && monthIndex == getCurrentMonthIndex()
    return if (isCurrentMonth) getCurrentDayOfMonth() else null
}

fun getNumberOfWeeksInMonth(monthInfo: MonthInfo): Int {
    return when (monthInfo.dayOfWeekOf1st) {
        0 -> if (monthInfo.numberOfDays == 28) 4 else 5
        5 -> if (monthInfo.numberOfDays == 31) 6 else 5
        6 -> if (monthInfo.numberOfDays >= 30) 6 else 5
        else -> 5
    }
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

fun getCurrentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)
fun getCurrentMonthIndex(): Int = Calendar.getInstance().get(Calendar.MONTH)
fun getCurrentDayOfMonth(): Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

fun isHoliday(
    dayValue: Int?,
    dayOfWeekIndex: Int,
    holidays: Map<Int, LocalString>,
    notHolidays: Map<Int, LocalString>
): Boolean {
    if (dayValue == null || dayValue in notHolidays) return false
    return dayOfWeekIndex > 4 || dayValue in holidays
}
