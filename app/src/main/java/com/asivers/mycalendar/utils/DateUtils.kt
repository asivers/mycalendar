package com.asivers.mycalendar.utils

import com.asivers.mycalendar.data.HolidaysForCountry
import com.asivers.mycalendar.data.MonthInfo
import java.util.Calendar
import java.util.GregorianCalendar

fun getMonthInfo(year: Int, monthIndex: Int, holidaysForCountry: HolidaysForCountry): MonthInfo {
    val firstOfThisMonth = GregorianCalendar(year, monthIndex, 1)
    val numberOfDays = firstOfThisMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
    val dayOfWeekOf1st = (firstOfThisMonth.get(Calendar.DAY_OF_WEEK) + 5) % 7
    val holidays = getMonthInfoHolidays(year, monthIndex, holidaysForCountry)
    val today = if (year == getCurrentYear() && monthIndex == getCurrentMonthIndex())
        getCurrentDayOfMonth() else null
    return MonthInfo(numberOfDays, dayOfWeekOf1st, holidays, today)
}

private fun getMonthInfoHolidays(
    year: Int, monthIndex:
    Int,
    holidaysForCountry: HolidaysForCountry
): Map<Int, String> {
    val holidays = mutableMapOf<Int, String>()
    val monthIndexFrom1To12 = monthIndex + 1
    if (monthIndexFrom1To12 in holidaysForCountry.everyYear) {
        holidays.putAll(holidaysForCountry.everyYear[monthIndexFrom1To12]!!)
    }
    if (year in holidaysForCountry.oneTime && monthIndexFrom1To12 in holidaysForCountry.oneTime[year]!!) {
        holidays.putAll(holidaysForCountry.oneTime[year]!![monthIndexFrom1To12]!!)
    }
    return holidays
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
    holidays: Map<Int, String>
): Boolean {
    return dayValue !== null && (dayOfWeekIndex > 4 || dayValue in holidays)
}
