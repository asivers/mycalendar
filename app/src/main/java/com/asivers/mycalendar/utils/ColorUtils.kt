package com.asivers.mycalendar.utils

import androidx.compose.ui.graphics.Brush
import com.asivers.mycalendar.constants.schemes.AUTUMN
import com.asivers.mycalendar.constants.schemes.SPRING
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.constants.schemes.WINTER
import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.enums.UserTheme
import com.asivers.mycalendar.enums.ViewShown

fun getColorScheme(selectedTheme: UserTheme, selectedMonthIndex: Int): ColorScheme {
    return when (selectedTheme) {
        UserTheme.THEME_SUMMER -> SUMMER
        UserTheme.THEME_AUTUMN -> AUTUMN
        UserTheme.THEME_WINTER -> WINTER
        UserTheme.THEME_SPRING -> SPRING
        UserTheme.CHANGE_BY_SEASON -> getColorSchemeByMonthIndex(selectedMonthIndex)
    }
}

private fun getColorSchemeByMonthIndex(selectedMonthIndex: Int): ColorScheme {
    return when (selectedMonthIndex) {
        11, 0, 1 -> WINTER
        2, 3, 4 -> SPRING
        5, 6, 7 -> SUMMER
        8, 9, 10 -> AUTUMN
        else -> throw IllegalArgumentException("incorrect selectedMonthIndex: $selectedMonthIndex")
    }
}

fun getMonthAndYearViewBackgroundGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.monthViewTop,
        0.12f to colorScheme.monthViewTop,
        0.27f to colorScheme.viewsBottom,
        1f to colorScheme.viewsBottom,
    )
)

fun getYearViewBackgroundGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.yearViewBtnTop,
        0.2f to colorScheme.yearViewBtnBottom,
        1f to colorScheme.viewsBottom,
    )
)

fun getSettingViewBackgroundGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.settingsViewTop,
        1f to colorScheme.viewsBottom,
    )
)

fun getDayViewBackgroundGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.monthViewTop,
        1f to colorScheme.viewsBottom,
    )
)

fun getBackgroundGradient(viewShown: ViewShown, colorScheme: ColorScheme): Brush {
    return when (viewShown) {
        ViewShown.MONTH, ViewShown.YEAR -> getMonthAndYearViewBackgroundGradient(colorScheme)
        ViewShown.DAY -> getDayViewBackgroundGradient(colorScheme)
        ViewShown.SETTINGS -> getSettingViewBackgroundGradient(colorScheme)
    }
}
