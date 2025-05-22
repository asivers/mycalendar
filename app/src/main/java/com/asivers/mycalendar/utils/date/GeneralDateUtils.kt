package com.asivers.mycalendar.utils.date

import com.asivers.mycalendar.data.HolidayInfo
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.data.scheme.CountryHolidayScheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun isLeapYear(year: Int): Boolean = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

fun getClosestLeapYear(year: Int): Int {
    var yearToCheck = year
    while (!isLeapYear(yearToCheck)) {
        yearToCheck++
    }
    return yearToCheck
}

fun getLengthOfMonth(year: Int, monthValue: Int): Int {
    return LocalDate.of(year, monthValue, 1).lengthOfMonth()
}

fun getDifferenceInDays(
    selectedDateInfo1: SelectedDateInfo,
    selectedDateInfo2: SelectedDateInfo
): Int {
    val date1 = selectedDateInfo1.getDate()
    val date2 = selectedDateInfo2.getDate()
    return ChronoUnit.DAYS.between(date2, date1).toInt()
}

fun getHolidayInfoForDay(
    selectedDateInfo: SelectedDateInfo,
    countryHolidayScheme: CountryHolidayScheme
): HolidayInfo? {
    val year = selectedDateInfo.year
    val monthValue = selectedDateInfo.monthValue
    val dayOfMonth = selectedDateInfo.dayOfMonth
    return countryHolidayScheme.everyYear[monthValue]?.get(dayOfMonth)
        ?: countryHolidayScheme.oneTime[year]?.get(monthValue)?.get(dayOfMonth)
}
