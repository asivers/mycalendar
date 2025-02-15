package com.asivers.mycalendar.utils

import android.content.Context
import com.asivers.mycalendar.data.AdjacentMonthsInfo
import com.asivers.mycalendar.data.DayInfo
import com.asivers.mycalendar.data.HolidayInfo
import com.asivers.mycalendar.data.HolidaysAndNotHolidays
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.data.scheme.CountryHolidayScheme
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekNumbersMode
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.proto.getDaysWithNotesForMonth
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun getMonthLength(year: Int, monthValue: Int): Int {
    return LocalDate.of(year, monthValue, 1).lengthOfMonth()
}

fun getMonthInfo(
    year: Int,
    monthValue: Int,
    countryHolidayScheme: CountryHolidayScheme,
    forView: ViewShown,
    ctx: Context? = null,
    thisDayOfMonth: Int? = null,
    weekNumbersMode: WeekNumbersMode = WeekNumbersMode.OFF
): MonthInfo {
    val firstOfThisMonth = LocalDate.of(year, monthValue, 1)
    val numberOfDays = firstOfThisMonth.lengthOfMonth()
    val dayOfWeekOf1st = (firstOfThisMonth.dayOfWeek.value + 6) % 7
    val holidaysAndNotHolidays = getHolidaysAndNotHolidays(year, monthValue, countryHolidayScheme)
    val today = getTodayValue(year, monthValue)
    if (forView == ViewShown.YEAR) {
        return MonthInfo(numberOfDays, dayOfWeekOf1st, holidaysAndNotHolidays, today)
    }
    val daysWithNotes = getDaysWithNotesForMonth(ctx!!, year, monthValue)
    val adjacentMonthsInfo = getAdjacentMonthsInfo(
        firstOfThisMonth, countryHolidayScheme, forView, ctx, numberOfDays, thisDayOfMonth)
    val weekNumbers = if (weekNumbersMode == WeekNumbersMode.ON)
        getWeekNumbers(firstOfThisMonth) else listOf()
    return MonthInfo(
        numberOfDays,
        dayOfWeekOf1st,
        holidaysAndNotHolidays,
        today,
        daysWithNotes,
        adjacentMonthsInfo,
        weekNumbers
    )
}

private fun getTodayValue(year: Int, monthValue: Int): Int? {
    val todayDate = LocalDate.now()
    val isCurrentMonth = year == todayDate.year && monthValue == todayDate.monthValue
    return if (isCurrentMonth) todayDate.dayOfMonth else null
}

// todo rewrite in kotlin style
private fun getHolidaysAndNotHolidays(
    year: Int,
    monthValue: Int,
    countryHolidayScheme: CountryHolidayScheme
): HolidaysAndNotHolidays {
    val holidays = mutableMapOf<Int, HolidayInfo>()
    val notHolidays = mutableMapOf<Int, HolidayInfo>()
    if (monthValue in countryHolidayScheme.everyYear) {
        countryHolidayScheme.everyYear[monthValue]!!.forEach {
            if (it.value.holiday != null) holidays[it.key] = it.value.holiday!!
            else if (it.value.notHoliday != null) notHolidays[it.key] = it.value.notHoliday!!
        }
    }
    if (year in countryHolidayScheme.oneTime && monthValue in countryHolidayScheme.oneTime[year]!!) {
        countryHolidayScheme.oneTime[year]!![monthValue]!!.forEach {
            if (it.value.holiday != null) holidays[it.key] = it.value.holiday!!
            else if (it.value.notHoliday != null) notHolidays[it.key] = it.value.notHoliday!!
        }
    }
    return HolidaysAndNotHolidays(holidays, notHolidays)
}

private fun getAdjacentMonthsInfo(
    firstOfThisMonth: LocalDate,
    countryHolidayScheme: CountryHolidayScheme,
    forView: ViewShown,
    ctx: Context,
    thisMonthNumberOfDays: Int,
    thisDayOfMonth: Int? = null
): AdjacentMonthsInfo {

    val firstOfPrevMonth = firstOfThisMonth.minusMonths(1)
    val prevMonthNumberOfDays = firstOfPrevMonth.lengthOfMonth()
    val prevMonthYear = firstOfPrevMonth.year
    val prevMonthMonthValue = firstOfPrevMonth.monthValue
    val prevMonthHolidaysAndNotHolidays = getHolidaysAndNotHolidays(
        year = prevMonthYear,
        monthValue = prevMonthMonthValue,
        countryHolidayScheme = countryHolidayScheme
    )
    val prevMonthToday = getTodayValue(prevMonthYear, prevMonthMonthValue)
    val prevMonthDaysWithNotes = if (forView == ViewShown.MONTH || thisDayOfMonth!! < 4)
        getDaysWithNotesForMonth(ctx, prevMonthYear, prevMonthMonthValue)
    else
        listOf()

    val firstOfNextMonth = firstOfThisMonth.plusMonths(1)
    val nextMonthYear = firstOfNextMonth.year
    val nextMonthMonthValue = firstOfNextMonth.monthValue
    val nextMonthHolidaysAndNotHolidays = getHolidaysAndNotHolidays(
        year = nextMonthYear,
        monthValue = nextMonthMonthValue,
        countryHolidayScheme = countryHolidayScheme
    )
    val nextMonthToday = getTodayValue(nextMonthYear, nextMonthMonthValue)
    val nextMonthDaysWithNotes = if (forView == ViewShown.MONTH || thisMonthNumberOfDays - thisDayOfMonth!! < 3)
        getDaysWithNotesForMonth(ctx, nextMonthYear, nextMonthMonthValue)
    else
        listOf()

    return AdjacentMonthsInfo(
        prevMonthNumberOfDays,
        prevMonthHolidaysAndNotHolidays,
        prevMonthToday,
        prevMonthDaysWithNotes,
        nextMonthHolidaysAndNotHolidays,
        nextMonthToday,
        nextMonthDaysWithNotes
    )
}

fun getDayInfo(
    dayValueRaw: Int, // including values outside of this month: -1, 0, 1, 2, ..., 30, 31, 32, 33 and so on
    monthInfo: MonthInfo,
    weekendMode: WeekendMode
): DayInfo {
    val inMonth = if (dayValueRaw <= 0)
        DisplayedMonth.PREVIOUS
    else if (dayValueRaw > monthInfo.numberOfDays)
        DisplayedMonth.NEXT
    else
        DisplayedMonth.THIS

    val dayValue: Int
    val holidaysAndNotHolidays: HolidaysAndNotHolidays
    val today: Int?
    val daysWithNotes: List<Int>

    if (inMonth == DisplayedMonth.THIS) {
        dayValue = dayValueRaw
        holidaysAndNotHolidays = monthInfo.holidaysAndNotHolidays
        today = monthInfo.today
        daysWithNotes = monthInfo.daysWithNotes
    } else {
        if (monthInfo.adjacentMonthsInfo == null) {
            return DayInfo(dayValueRaw, inMonth)
        }
        if (inMonth == DisplayedMonth.PREVIOUS) {
            dayValue = dayValueRaw + monthInfo.adjacentMonthsInfo.prevMonthNumberOfDays
            holidaysAndNotHolidays = monthInfo.adjacentMonthsInfo.prevMonthHolidaysAndNotHolidays
            today = monthInfo.adjacentMonthsInfo.prevMonthToday
            daysWithNotes = monthInfo.adjacentMonthsInfo.prevMonthDaysWithNotes
        } else {
            dayValue = dayValueRaw - monthInfo.numberOfDays
            holidaysAndNotHolidays = monthInfo.adjacentMonthsInfo.nextMonthHolidaysAndNotHolidays
            today = monthInfo.adjacentMonthsInfo.nextMonthToday
            daysWithNotes = monthInfo.adjacentMonthsInfo.nextMonthDaysWithNotes
        }
    }

    val isToday = dayValue == today
    val dayOfWeekValue = ((dayValueRaw + monthInfo.dayOfWeekOf1st + 6) % 7) + 1
    val isWeekend = isWeekend(dayValue, dayOfWeekValue, weekendMode, holidaysAndNotHolidays)
    val isHoliday = isHoliday(dayValue, holidaysAndNotHolidays)
    val isWithNote = dayValue in daysWithNotes

    return DayInfo(dayValue, inMonth, isToday, dayOfWeekValue, isWeekend, isHoliday, isWithNote)
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
    return countryHolidayScheme.everyYear[monthValue]?.get(dayOfMonth)?.holiday
        ?: countryHolidayScheme.oneTime[year]?.get(monthValue)?.get(dayOfMonth)?.holiday
}
