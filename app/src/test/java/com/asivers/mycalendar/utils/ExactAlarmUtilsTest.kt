package com.asivers.mycalendar.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class ExactAlarmUtilsTest {

    @Test
    fun toAlarmTimeInMillis_isCorrect_1() {
        if (ZoneId.systemDefault().toString() != "Europe/Madrid") return

        val daysDiff = 90L

        val ldt1 = LocalDateTime.of(2025, 9, 1, 12, 0)
        val ldt2 = ldt1.plusDays(daysDiff)

        val millis1 = ldt1.toAlarmTimeInMillis()
        val millis2 = ldt2.toAlarmTimeInMillis()

        val millisDif = millis2 - millis1

        assertEquals(daysToMillis(daysDiff) + 3600000L, millisDif)
    }

    @Test
    fun toAlarmTimeInMillis_isCorrect_2() {
        if (ZoneId.systemDefault().toString() != "Europe/Madrid") return

        val daysDiff = 60L

        val ldt1 = LocalDateTime.of(2025, 12, 1, 12, 0)
        val ldt2 = ldt1.plusDays(daysDiff)

        val millis1 = ldt1.toAlarmTimeInMillis()
        val millis2 = ldt2.toAlarmTimeInMillis()

        val millisDif = millis2 - millis1

        assertEquals(daysToMillis(daysDiff), millisDif)
    }

    @Test
    fun toAlarmTimeInMillis_isCorrect_3() {
        if (ZoneId.systemDefault().toString() != "Europe/Madrid") return

        val daysDiff = 90L

        val ldt1 = LocalDateTime.of(2026, 2, 1, 12, 0)
        val ldt2 = ldt1.plusDays(daysDiff)

        val millis1 = ldt1.toAlarmTimeInMillis()
        val millis2 = ldt2.toAlarmTimeInMillis()

        val millisDif = millis2 - millis1

        assertEquals(daysToMillis(daysDiff) - 3600000L, millisDif)
    }

    private fun daysToMillis(days: Long): Long = days * 24L * 60L * 60L * 1000L

}
