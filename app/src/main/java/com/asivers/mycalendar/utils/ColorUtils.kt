package com.asivers.mycalendar.utils

import androidx.compose.ui.graphics.Brush
import com.asivers.mycalendar.constants.schemes.AUTUMN
import com.asivers.mycalendar.constants.schemes.SPRING
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.constants.schemes.WINTER
import com.asivers.mycalendar.data.scheme.ColorScheme

fun getColorScheme(selectedMonthIndex: Int): ColorScheme {
    return when (selectedMonthIndex) {
        11, 0, 1 -> WINTER
        2, 3, 4 -> SPRING
        5, 6, 7 -> SUMMER
        8, 9, 10 -> AUTUMN
        else -> throw IllegalArgumentException("incorrect selectedMonthIndex: $selectedMonthIndex")
    }
}

fun getMonthViewBackgroundGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.mvLight,
        0.1f to colorScheme.mvLight,
        0.25f to colorScheme.myvDark,
        1f to colorScheme.myvDark,
    )
)

fun getYearViewBackgroundGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.yvLight,
        0.2f to colorScheme.yvMedium,
        1f to colorScheme.myvDark,
    )
)
