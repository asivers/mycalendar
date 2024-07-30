package com.example.mycalendar

import java.time.Month

val holidayIndexes = intArrayOf(5, 6, 12, 13, 19, 20, 26, 27, 33, 34, 40, 41)

// todo must be set by user
val holidayDatesEveryYear: Map<Month, List<Int>> = mapOf(
    Month.JANUARY to listOf(
        1, // Spain
        6, // Spain
    ),
    Month.MAY to listOf(
        1, // Spain
    ),
    Month.JUNE to listOf(
        24, // Catalonia
        29, // Reus
    ),
    Month.AUGUST to listOf(
        15, // Spain
    ),
    Month.SEPTEMBER to listOf(
        11, // Catalonia
        25, // Reus
    ),
    Month.OCTOBER to listOf(
        12, // Spain
    ),
    Month.NOVEMBER to listOf(
        1, // Spain
    ),
    Month.DECEMBER to listOf(
        6, // Spain
        8, // Spain
        25, // Spain
        26, // Catalonia
    ),
)

// todo must be set by user
val holidayDatesOneTime: Map<Int, Map<Month, List<Int>>> = mapOf(
    2024 to mapOf(
        Month.MARCH to listOf(
            29, // Spain (Easter)
        ),
        Month.APRIL to listOf(
            1, // Catalonia (Easter)
        ),
    ),
)
