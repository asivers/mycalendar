package com.asivers.mycalendar.utils

import com.asivers.mycalendar.data.DayInfo
import com.asivers.mycalendar.data.HolidaysForCountry
import com.asivers.mycalendar.data.LocalString
import java.util.Calendar

fun getHolidaysForCountryForPreview(): HolidaysForCountry {
    return HolidaysForCountry(
        everyYear = mapOf(
            Calendar.JANUARY to mapOf(
                Pair(1, DayInfo(holiday = LocalString(en = "España - Año Nuevo"))),
                Pair(6, DayInfo(holiday = LocalString(en = "España - Epifanía del Señor")))
            ),
            Calendar.MAY to mapOf(
                Pair(1, DayInfo(holiday = LocalString(en = "España - Día del Trabajador")))
            ),
            Calendar.JUNE to mapOf(
                Pair(24, DayInfo(holiday = LocalString(en = "Cataluña - Sant Joan")))
            ),
            Calendar.AUGUST to mapOf(
                Pair(15, DayInfo(holiday = LocalString(en = "España - Asunción")))
            ),
            Calendar.SEPTEMBER to mapOf(
                Pair(11, DayInfo(holiday = LocalString(en = "Cataluña - Diada Nacional de Catalunya"))),
                Pair(25, DayInfo(holiday = LocalString(en = "Reus - Misericòrdia")))
            ),
            Calendar.OCTOBER to mapOf(
                Pair(12, DayInfo(holiday = LocalString(en = "España - Fiesta Nacional de España")))
            ),
            Calendar.NOVEMBER to mapOf(
                Pair(1, DayInfo(holiday = LocalString(en = "España - Día de todos los Santos")))
            ),
            Calendar.DECEMBER to mapOf(
                Pair(6, DayInfo(holiday = LocalString(en = "España - Dia de la Constitucion Espanola"))),
                Pair(8, DayInfo(holiday = LocalString(en = "España - Inmaculada Concepción"))),
                Pair(25, DayInfo(holiday = LocalString(en = "España - Navidad"))),
                Pair(26, DayInfo(holiday = LocalString(en = "Cataluña - Sant Esteve")))
            )
        ),
        oneTime = mapOf(
            2024 to mapOf(
                Calendar.MARCH to mapOf(
                    Pair(29, DayInfo(holiday = LocalString(en = "España - Viernes Santo")))
                ),
                Calendar.APRIL to mapOf(
                    Pair(1, DayInfo(holiday = LocalString(en = "Cataluña - Lunes de Pascua")))
                ),
                Calendar.JUNE to mapOf(
                    Pair(29, DayInfo(holiday = LocalString(en = "Reus - Sant Pere")))
                )
            ),
            2025 to mapOf(
                Calendar.APRIL to mapOf(
                    Pair(18, DayInfo(holiday = LocalString(en = "España - Viernes Santo"))),
                    Pair(21, DayInfo(holiday = LocalString(en = "Cataluña - Lunes de Pascua")))
                ),
                Calendar.JUNE to mapOf(
                    Pair(30, DayInfo(holiday = LocalString(en = "Reus - Sant Pere")))
                )
            )
        )
    )
}
