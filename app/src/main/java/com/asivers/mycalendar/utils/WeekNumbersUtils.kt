package com.asivers.mycalendar.utils

import java.time.LocalDate
import java.time.Month

internal fun testGetWeekNumbers(
    year: Int,
    month: Month
): List<Int> {
    return getWeekNumbers(LocalDate.of(year, month, 1))
}

fun getWeekNumbers(
    firstOfThisMonth: LocalDate
): List<Int> {
    val firstWeekNumber = getWeekNumber(firstOfThisMonth)
    if (firstWeekNumber >= 52) {
        return listOf(firstWeekNumber, 1, 2, 3, 4, 5)
    }
    if (firstOfThisMonth.month == Month.DECEMBER) {
        val lastOfThisMonth = LocalDate.of(firstOfThisMonth.year, Month.DECEMBER, 31)
        return if (lastOfThisMonth.dayOfWeek.value == 3) {
            listOf(firstWeekNumber, firstWeekNumber + 1, firstWeekNumber + 2,
                firstWeekNumber + 3, 1, 2)
        } else {
            listOf(firstWeekNumber, firstWeekNumber + 1, firstWeekNumber + 2,
                firstWeekNumber + 3, firstWeekNumber + 4, 1)
        }
    }
    return List(6) { firstWeekNumber + it }
}

private fun getWeekNumber(
    date: LocalDate
): Int {
    val firstOfThisYear = LocalDate.of(date.year, Month.JANUARY, 1)
    val dayOfWeekOfFirstOfThisYear = firstOfThisYear.dayOfWeek.value
    val weekNumber: Int
    if (dayOfWeekOfFirstOfThisYear <= 4) {
        weekNumber = (date.dayOfYear + firstOfThisYear.dayOfWeek.value + 5) / 7
        return weekNumber
    }
    weekNumber = (date.dayOfYear + firstOfThisYear.dayOfWeek.value - 2) / 7
    if (weekNumber > 0) {
        return weekNumber
    }
    return getWeekNumber(firstOfThisYear.minusDays(1))
}
