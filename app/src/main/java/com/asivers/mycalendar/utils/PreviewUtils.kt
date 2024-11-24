package com.asivers.mycalendar.utils

import com.asivers.mycalendar.data.HolidaysForCountry
import java.util.Calendar

fun getHolidaysForCountryForPreview(): HolidaysForCountry {
    return HolidaysForCountry(
        everyYear = mapOf(
            Calendar.JANUARY to mapOf(
                Pair(1, "España - Año Nuevo"),
                Pair(6, "España - Epifanía del Señor")
            ),
            Calendar.MAY to mapOf(
                Pair(1, "España - Día del Trabajador")
            ),
            Calendar.JUNE to mapOf(
                Pair(24, "Cataluña - Sant Joan"),
                Pair(29, "Reus - Sant Pere")
            ),
            Calendar.AUGUST to mapOf(
                Pair(15, "España - Asunción")
            ),
            Calendar.SEPTEMBER to mapOf(
                Pair(11, "Cataluña - Diada Nacional de Catalunya"),
                Pair(25, "Reus - Misericòrdia")
            ),
            Calendar.OCTOBER to mapOf(
                Pair(12, "España - Fiesta Nacional de España")
            ),
            Calendar.NOVEMBER to mapOf(
                Pair(1, "España - Día de todos los Santos")
            ),
            Calendar.DECEMBER to mapOf(
                Pair(6, "España - Dia de la Constitucion Espanola"),
                Pair(8, "España - Inmaculada Concepción"),
                Pair(25, "España - Navidad"),
                Pair(26, "Cataluña - Sant Esteve")
            ),
        ),
        oneTime = mapOf(
            2024 to mapOf(
                Calendar.MARCH to mapOf(
                    Pair(29, "España - Pascua de Resurrección")
                ),
                Calendar.APRIL to mapOf(
                    Pair(1, "Cataluña - Pasqua")
                ),
            ),
        )
    )
}
