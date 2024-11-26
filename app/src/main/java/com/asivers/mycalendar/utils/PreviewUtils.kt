package com.asivers.mycalendar.utils

import com.asivers.mycalendar.data.DayInfo
import com.asivers.mycalendar.data.HolidaysForCountry
import com.asivers.mycalendar.data.LocalString
import com.asivers.mycalendar.data.scheme.TranslationsScheme
import java.util.Calendar

fun getHolidaysForCountryForPreview(): HolidaysForCountry {
    return HolidaysForCountry(
        everyYear = mapOf(
            Calendar.JANUARY to mapOf(
                Pair(1, DayInfo(holiday = LocalString(en = "Spain - New Year's Day", es = "España - Año Nuevo"))),
                Pair(6, DayInfo(holiday = LocalString(en = "Spain - Epiphany", es = "España - Epifanía del Señor")))
            ),
            Calendar.MAY to mapOf(
                Pair(1, DayInfo(holiday = LocalString(en = "Spain - Labour Day", es = "España - Día del Trabajador")))
            ),
            Calendar.JUNE to mapOf(
                Pair(24, DayInfo(holiday = LocalString(en = "Catalonia - John the Baptist", es = "Cataluña - Sant Joan")))
            ),
            Calendar.AUGUST to mapOf(
                Pair(15, DayInfo(holiday = LocalString(en = "Spain - Assumption", es = "España - Asunción")))
            ),
            Calendar.SEPTEMBER to mapOf(
                Pair(11, DayInfo(holiday = LocalString(en = "Catalonia - National Day of Catalonia", es = "Cataluña - Diada Nacional de Catalunya"))),
                Pair(25, DayInfo(holiday = LocalString(en = "Reus - Misericordia", es = "Reus - Misericòrdia")))
            ),
            Calendar.OCTOBER to mapOf(
                Pair(12, DayInfo(holiday = LocalString(en = "Spain - National Day of Spain", es = "España - Fiesta Nacional de España")))
            ),
            Calendar.NOVEMBER to mapOf(
                Pair(1, DayInfo(holiday = LocalString(en = "Spain - All Saints' Day", es = "España - Día de todos los Santos")))
            ),
            Calendar.DECEMBER to mapOf(
                Pair(6, DayInfo(holiday = LocalString(en = "Spain - Constitution Day", es = "España - Dia de la Constitucion Espanola"))),
                Pair(8, DayInfo(holiday = LocalString(en = "Spain - Immaculate Conception", es = "España - Inmaculada Concepción"))),
                Pair(25, DayInfo(holiday = LocalString(en = "Spain - Christmas Day", es = "España - Navidad"))),
                Pair(26, DayInfo(holiday = LocalString(en = "Catalonia - St. Stephen's Day", es = "Cataluña - Sant Esteve")))
            )
        ),
        oneTime = mapOf(
            2024 to mapOf(
                Calendar.MARCH to mapOf(
                    Pair(29, DayInfo(holiday = LocalString(en = "Spain - Good Friday", es = "España - Viernes Santo")))
                ),
                Calendar.APRIL to mapOf(
                    Pair(1, DayInfo(holiday = LocalString(en = "Catalonia - Easter Monday", es = "Cataluña - Lunes de Pascua")))
                ),
                Calendar.JUNE to mapOf(
                    Pair(29, DayInfo(holiday = LocalString(en = "Reus - Saint Peter", es = "Reus - Sant Pere")))
                )
            ),
            2025 to mapOf(
                Calendar.APRIL to mapOf(
                    Pair(18, DayInfo(holiday = LocalString(en = "Spain - Good Friday", es = "España - Viernes Santo"))),
                    Pair(21, DayInfo(holiday = LocalString(en = "Catalonia - Easter Monday", es = "Cataluña - Lunes de Pascua")))
                ),
                Calendar.JUNE to mapOf(
                    Pair(30, DayInfo(holiday = LocalString(en = "Reus - Saint Peter", es = "Reus - Sant Pere")))
                )
            )
        )
    )
}

fun getTranslationsSchemeForPreview(): TranslationsScheme {
    return TranslationsScheme(
        yearView = "Year view",
        months = listOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        ),
        daysOfWeek3 = listOf(
            "Mon",
            "Tue",
            "Wed",
            "Thu",
            "Fri",
            "Sat",
            "Sun"
        ),
        daysOfWeek1 = listOf(
            "M",
            "T",
            "W",
            "T",
            "F",
            "S",
            "S"
        )
    )
}
