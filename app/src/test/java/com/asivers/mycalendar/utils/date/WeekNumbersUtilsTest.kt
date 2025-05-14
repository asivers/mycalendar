package com.asivers.mycalendar.utils.date

import org.junit.Test

import org.junit.Assert.assertEquals
import java.time.Month

class WeekNumbersUtilsTest {
    @Test
    fun getWeekNumbers_isCorrect() {
        assertEquals(
            listOf(49, 50, 51, 52, 53, 1),
            testGetWeekNumbers(2020, Month.DECEMBER)
        )
        assertEquals(
            listOf(53, 1, 2, 3, 4, 5),
            testGetWeekNumbers(2021, Month.JANUARY)
        )
        assertEquals(
            listOf(52, 1, 2, 3, 4, 5),
            testGetWeekNumbers(2023, Month.JANUARY)
        )
        assertEquals(
            listOf(5, 6, 7, 8, 9, 10),
            testGetWeekNumbers(2023, Month.FEBRUARY)
        )
        assertEquals(
            listOf(48, 49, 50, 51, 52, 1),
            testGetWeekNumbers(2023, Month.DECEMBER)
        )
        assertEquals(
            listOf(18, 19, 20, 21, 22, 23),
            testGetWeekNumbers(2024, Month.MAY)
        )
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6),
            testGetWeekNumbers(2025, Month.JANUARY)
        )
        assertEquals(
            listOf(49, 50, 51, 52, 1, 2),
            testGetWeekNumbers(2025, Month.DECEMBER)
        )
    }
}
