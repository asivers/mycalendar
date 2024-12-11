package com.asivers.mycalendar.utils

import com.asivers.mycalendar.data.AdjacentMonthsInfo
import com.asivers.mycalendar.data.DayInMonthGridInfo
import com.asivers.mycalendar.data.HolidayInfo
import com.asivers.mycalendar.data.HolidaysAndNotHolidays
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.scheme.CountryHolidayScheme
import com.asivers.mycalendar.enums.WeekendMode
import java.util.Calendar
import java.util.GregorianCalendar

fun getCurrentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)
fun getCurrentMonthIndex(): Int = Calendar.getInstance().get(Calendar.MONTH)
fun getCurrentDayOfMonth(): Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

fun getMonthInfo(
    year: Int,
    monthIndex: Int,
    countryHolidayScheme: CountryHolidayScheme,
    forYearView: Boolean
): MonthInfo {
    val firstOfThisMonth = GregorianCalendar(year, monthIndex, 1)
    val numberOfDays = firstOfThisMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
    val dayOfWeekOf1st = (firstOfThisMonth.get(Calendar.DAY_OF_WEEK) + 5) % 7
    val holidaysAndNotHolidays = getHolidaysAndNotHolidays(year, monthIndex, countryHolidayScheme)
    val today = getTodayValue(year, monthIndex)
    val adjacentMonthsInfo = if (forYearView)
        null else getAdjacentMonthsInfo(firstOfThisMonth, countryHolidayScheme)
    return MonthInfo(
        numberOfDays,
        dayOfWeekOf1st,
        holidaysAndNotHolidays,
        today,
        adjacentMonthsInfo
    )
}

private fun getTodayValue(year: Int, monthIndex: Int): Int? {
    val isCurrentMonth = year == getCurrentYear() && monthIndex == getCurrentMonthIndex()
    return if (isCurrentMonth) getCurrentDayOfMonth() else null
}

private fun getHolidaysAndNotHolidays(
    year: Int,
    monthIndex: Int,
    countryHolidayScheme: CountryHolidayScheme
): HolidaysAndNotHolidays {
    val monthIndexFrom1To12 = monthIndex + 1
    val holidays = mutableMapOf<Int, HolidayInfo>()
    val notHolidays = mutableMapOf<Int, HolidayInfo>()
    if (monthIndexFrom1To12 in countryHolidayScheme.everyYear) {
        countryHolidayScheme.everyYear[monthIndexFrom1To12]!!.forEach {
            if (it.value.holiday != null) holidays[it.key] = it.value.holiday!!
            else if (it.value.notHoliday != null) notHolidays[it.key] = it.value.notHoliday!!
        }
    }
    if (year in countryHolidayScheme.oneTime && monthIndexFrom1To12 in countryHolidayScheme.oneTime[year]!!) {
        countryHolidayScheme.oneTime[year]!![monthIndexFrom1To12]!!.forEach {
            if (it.value.holiday != null) holidays[it.key] = it.value.holiday!!
            else if (it.value.notHoliday != null) notHolidays[it.key] = it.value.notHoliday!!
        }
    }
    return HolidaysAndNotHolidays(holidays, notHolidays)
}

private fun getAdjacentMonthsInfo(
    firstOfMonth: GregorianCalendar, // now it is current month
    countryHolidayScheme: CountryHolidayScheme,
): AdjacentMonthsInfo {

    firstOfMonth.add(Calendar.MONTH, -1) // now it is previous month
    val prevMonthNumberOfDays = firstOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
    val prevMonthYear = firstOfMonth.get(Calendar.YEAR)
    val prevMonthMonthIndex = firstOfMonth.get(Calendar.MONTH)
    val prevMonthHolidaysAndNotHolidays = getHolidaysAndNotHolidays(
        year = prevMonthYear,
        monthIndex = prevMonthMonthIndex,
        countryHolidayScheme = countryHolidayScheme
    )
    val prevMonthToday = getTodayValue(prevMonthYear, prevMonthMonthIndex)

    firstOfMonth.add(Calendar.MONTH, 2) // now it is next month
    val nextMonthYear = firstOfMonth.get(Calendar.YEAR)
    val nextMonthMonthIndex = firstOfMonth.get(Calendar.MONTH)
    val nextMonthHolidaysAndNotHolidays = getHolidaysAndNotHolidays(
        year = nextMonthYear,
        monthIndex = nextMonthMonthIndex,
        countryHolidayScheme = countryHolidayScheme
    )
    val nextMonthToday = getTodayValue(nextMonthYear, nextMonthMonthIndex)

    return AdjacentMonthsInfo(
        prevMonthNumberOfDays,
        prevMonthHolidaysAndNotHolidays,
        prevMonthToday,
        nextMonthHolidaysAndNotHolidays,
        nextMonthToday
    )
}

fun getDayInMonthGridInfo(
    dayValueRaw: Int, // including values outside of this month: -1, 0, 1, 2, ..., 30, 31, 32, 33 and so on
    monthInfo: MonthInfo,
    weekendMode: WeekendMode
): DayInMonthGridInfo {
    val inPrevMonth = dayValueRaw <= 0
    val inThisMonth = dayValueRaw in 1..monthInfo.numberOfDays

    val dayValue: Int
    val holidaysAndNotHolidays: HolidaysAndNotHolidays
    val today: Int?

    if (inThisMonth) {
        dayValue = dayValueRaw
        holidaysAndNotHolidays = monthInfo.holidaysAndNotHolidays
        today = monthInfo.today
    } else {
        if (monthInfo.adjacentMonthsInfo == null) {
            return DayInMonthGridInfo(dayValueRaw, false)
        }
        if (inPrevMonth) {
            dayValue = dayValueRaw + monthInfo.adjacentMonthsInfo.prevMonthNumberOfDays
            holidaysAndNotHolidays = monthInfo.adjacentMonthsInfo.prevMonthHolidaysAndNotHolidays
            today = monthInfo.adjacentMonthsInfo.prevMonthToday
        } else {
            dayValue = dayValueRaw - monthInfo.numberOfDays
            holidaysAndNotHolidays = monthInfo.adjacentMonthsInfo.nextMonthHolidaysAndNotHolidays
            today = monthInfo.adjacentMonthsInfo.nextMonthToday
        }
    }

    val isToday = dayValue == today
    val dayOfWeekIndex = (dayValueRaw + monthInfo.dayOfWeekOf1st - 1) % 7
    val isWeekend = isWeekend(dayValue, dayOfWeekIndex, weekendMode, holidaysAndNotHolidays)
    val isHoliday = isHoliday(dayValue, holidaysAndNotHolidays)

    return DayInMonthGridInfo(dayValue, inThisMonth, isToday, isWeekend, isHoliday)
}

private fun isWeekend(
    dayValue: Int,
    dayOfWeekIndex: Int,
    weekendMode: WeekendMode,
    holidaysAndNotHolidays: HolidaysAndNotHolidays
): Boolean {
    if (weekendMode == WeekendMode.NO_DISPLAY || dayValue in holidaysAndNotHolidays.notHolidays) {
        return false
    }
    if (weekendMode == WeekendMode.ONLY_SUNDAY) {
        return dayOfWeekIndex == 6
    }
    return dayOfWeekIndex >= 5
}

private fun isHoliday(dayValue: Int, holidaysAndNotHolidays: HolidaysAndNotHolidays): Boolean {
    return dayValue !in holidaysAndNotHolidays.notHolidays &&
            dayValue in holidaysAndNotHolidays.holidays
}
