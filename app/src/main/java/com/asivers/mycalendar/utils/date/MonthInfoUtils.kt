package com.asivers.mycalendar.utils.date

import android.content.Context
import com.asivers.mycalendar.data.AdjacentMonthsInfo
import com.asivers.mycalendar.data.BaseMonthInfo
import com.asivers.mycalendar.data.HolidayInfo
import com.asivers.mycalendar.data.HolidaysAndNotHolidays
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.scheme.CountryHolidayScheme
import com.asivers.mycalendar.enums.WeekNumbersMode
import com.asivers.mycalendar.utils.proto.getDaysWithNotesForMonth
import java.time.LocalDate

fun getBaseMonthInfo(
    year: Int,
    monthValue: Int,
    countryHolidayScheme: CountryHolidayScheme
): BaseMonthInfo {
    val firstOfThisMonth = LocalDate.of(year, monthValue, 1)
    val lengthOfMonth = firstOfThisMonth.lengthOfMonth()
    val dayOfWeekOf1st = firstOfThisMonth.dayOfWeek.value
    val holidaysAndNotHolidays = getHolidaysAndNotHolidays(year, monthValue, countryHolidayScheme)
    val today = getTodayValue(year, monthValue)
    return BaseMonthInfo(lengthOfMonth, dayOfWeekOf1st, holidaysAndNotHolidays, today)
}

fun getMonthInfoForMonthView(
    ctx: Context,
    year: Int,
    monthValue: Int,
    countryHolidayScheme: CountryHolidayScheme,
    weekNumbersMode: WeekNumbersMode
): MonthInfo {
    val baseMonthInfo = getBaseMonthInfo(year, monthValue, countryHolidayScheme)
    val firstOfThisMonth = LocalDate.of(year, monthValue, 1)
    val daysWithNotes = getDaysWithNotesForMonth(ctx, year, monthValue)
    val adjacentMonthsInfo = getAdjacentMonthsInfo(ctx, firstOfThisMonth, countryHolidayScheme)
    val weekNumbers = if (weekNumbersMode == WeekNumbersMode.ON)
        getWeekNumbers(firstOfThisMonth) else listOf()
    return MonthInfo(baseMonthInfo, daysWithNotes, adjacentMonthsInfo, weekNumbers)
}

fun getMonthInfoForDayView(
    ctx: Context,
    year: Int,
    monthValue: Int,
    countryHolidayScheme: CountryHolidayScheme,
    thisDayOfMonth: Int
): MonthInfo {
    val baseMonthInfo = getBaseMonthInfo(year, monthValue, countryHolidayScheme)
    val firstOfThisMonth = LocalDate.of(year, monthValue, 1)
    val daysWithNotes = getDaysWithNotesForMonth(ctx, year, monthValue)
    val needNotesForPrevMonth = thisDayOfMonth < 4
    val needNotesForNextMonth = baseMonthInfo.lengthOfMonth - thisDayOfMonth < 3
    val adjacentMonthsInfo = getAdjacentMonthsInfo(
        ctx, firstOfThisMonth, countryHolidayScheme, needNotesForPrevMonth, needNotesForNextMonth)
    return MonthInfo(baseMonthInfo, daysWithNotes, adjacentMonthsInfo)
}

private fun getTodayValue(year: Int, monthValue: Int): Int? {
    val todayDate = LocalDate.now()
    val isCurrentMonth = year == todayDate.year && monthValue == todayDate.monthValue
    return if (isCurrentMonth) todayDate.dayOfMonth else null
}

private fun getHolidaysAndNotHolidays(
    year: Int,
    monthValue: Int,
    countryHolidayScheme: CountryHolidayScheme
): HolidaysAndNotHolidays {
    val holidays = mutableMapOf<Int, HolidayInfo>()
    val notHolidays = mutableMapOf<Int, HolidayInfo>()
    countryHolidayScheme.everyYear[monthValue]?.forEach { (dayValue, holidayInfo) ->
        if (holidayInfo.notHoliday)
            notHolidays[dayValue] = holidayInfo else holidays[dayValue] = holidayInfo
    }
    countryHolidayScheme.oneTime[year]?.get(monthValue)?.forEach { (dayValue, holidayInfo) ->
        if (holidayInfo.notHoliday)
            notHolidays[dayValue] = holidayInfo else holidays[dayValue] = holidayInfo
    }
    return HolidaysAndNotHolidays(holidays, notHolidays)
}

private fun getAdjacentMonthsInfo(
    ctx: Context,
    firstOfThisMonth: LocalDate,
    countryHolidayScheme: CountryHolidayScheme,
    needNotesForPrevMonth: Boolean = true,
    needNotesForNextMonth: Boolean = true
): AdjacentMonthsInfo {

    val firstOfPrevMonth = firstOfThisMonth.minusMonths(1)
    val prevMonthNumberOfDays = firstOfPrevMonth.lengthOfMonth()
    val prevMonthYear = firstOfPrevMonth.year
    val prevMonthMonthValue = firstOfPrevMonth.monthValue
    val prevMonthHolidaysAndNotHolidays = getHolidaysAndNotHolidays(
        prevMonthYear, prevMonthMonthValue, countryHolidayScheme)
    val prevMonthToday = getTodayValue(prevMonthYear, prevMonthMonthValue)
    val prevMonthDaysWithNotes = if (needNotesForPrevMonth)
        getDaysWithNotesForMonth(ctx, prevMonthYear, prevMonthMonthValue) else listOf()

    val firstOfNextMonth = firstOfThisMonth.plusMonths(1)
    val nextMonthYear = firstOfNextMonth.year
    val nextMonthMonthValue = firstOfNextMonth.monthValue
    val nextMonthHolidaysAndNotHolidays = getHolidaysAndNotHolidays(
        nextMonthYear, nextMonthMonthValue, countryHolidayScheme)
    val nextMonthToday = getTodayValue(nextMonthYear, nextMonthMonthValue)
    val nextMonthDaysWithNotes = if (needNotesForNextMonth)
        getDaysWithNotesForMonth(ctx, nextMonthYear, nextMonthMonthValue) else listOf()

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
