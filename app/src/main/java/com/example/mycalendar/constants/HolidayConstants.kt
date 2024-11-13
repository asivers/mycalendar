package com.example.mycalendar.constants

import com.example.mycalendar.data.HolidaysInfo
import java.util.Calendar

// todo must be set by user
val DEFAULT_HOLIDAYS_INFO = HolidaysInfo(
    holidayDatesEveryYear = mapOf(
        Calendar.JANUARY to listOf(
            1, // Spain
            6, // Spain
        ),
        Calendar.MAY to listOf(
            1, // Spain
        ),
        Calendar.JUNE to listOf(
            24, // Catalonia
            29, // Reus
        ),
        Calendar.AUGUST to listOf(
            15, // Spain
        ),
        Calendar.SEPTEMBER to listOf(
            11, // Catalonia
            25, // Reus
        ),
        Calendar.OCTOBER to listOf(
            12, // Spain
        ),
        Calendar.NOVEMBER to listOf(
            1, // Spain
        ),
        Calendar.DECEMBER to listOf(
            6, // Spain
            8, // Spain
            25, // Spain
            26, // Catalonia
        ),
    ),
    holidayDatesOneTime = mapOf(
        2024 to mapOf(
            Calendar.MARCH to listOf(
                29, // Spain (Easter)
            ),
            Calendar.APRIL to listOf(
                1, // Catalonia (Easter)
            ),
        ),
    )
)
