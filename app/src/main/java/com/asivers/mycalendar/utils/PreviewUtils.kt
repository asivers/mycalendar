package com.asivers.mycalendar.utils

import com.asivers.mycalendar.data.DayInfo
import com.asivers.mycalendar.data.HolidaysForCountry
import java.util.Calendar

fun getHolidaysForCountryForPreview(): HolidaysForCountry {
    return HolidaysForCountry(
        everyYear = mapOf(
            Calendar.JANUARY to mapOf(
                Pair(1, DayInfo("España - Año Nuevo")),
                Pair(6, DayInfo("España - Epifanía del Señor"))
            ),
            Calendar.MAY to mapOf(
                Pair(1, DayInfo("España - Día del Trabajador"))
            ),
            Calendar.JUNE to mapOf(
                Pair(24, DayInfo("Cataluña - Sant Joan"))
            ),
            Calendar.AUGUST to mapOf(
                Pair(15, DayInfo("España - Asunción"))
            ),
            Calendar.SEPTEMBER to mapOf(
                Pair(11, DayInfo("Cataluña - Diada Nacional de Catalunya")),
                Pair(25, DayInfo("Reus - Misericòrdia"))
            ),
            Calendar.OCTOBER to mapOf(
                Pair(12, DayInfo("España - Fiesta Nacional de España"))
            ),
            Calendar.NOVEMBER to mapOf(
                Pair(1, DayInfo("España - Día de todos los Santos"))
            ),
            Calendar.DECEMBER to mapOf(
                Pair(6, DayInfo("España - Dia de la Constitucion Espanola")),
                Pair(8, DayInfo("España - Inmaculada Concepción")),
                Pair(25, DayInfo("España - Navidad")),
                Pair(26, DayInfo("Cataluña - Sant Esteve"))
            )
        ),
        oneTime = mapOf(
            2024 to mapOf(
                Calendar.MARCH to mapOf(
                    Pair(29, DayInfo("España - Viernes Santo"))
                ),
                Calendar.APRIL to mapOf(
                    Pair(1, DayInfo("Cataluña - Lunes de Pascua"))
                ),
                Calendar.JUNE to mapOf(
                    Pair(29, DayInfo("Reus - Sant Pere"))
                )
            ),
            2025 to mapOf(
                Calendar.APRIL to mapOf(
                    Pair(18, DayInfo("España - Viernes Santo")),
                    Pair(21, DayInfo("Cataluña - Lunes de Pascua"))
                ),
                Calendar.JUNE to mapOf(
                    Pair(30, DayInfo("Reus - Sant Pere"))
                )
            )
        )
    )
}
