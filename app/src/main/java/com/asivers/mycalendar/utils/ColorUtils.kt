package com.asivers.mycalendar.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.asivers.mycalendar.constants.schemes.AUTUMN
import com.asivers.mycalendar.constants.schemes.SPRING
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.constants.schemes.WINTER
import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.enums.ViewShown

fun getColorSchemeByMonthValue(selectedMonthValue: Int): ColorScheme {
    return when (selectedMonthValue) {
        12, 1, 2 -> WINTER
        3, 4, 5 -> SPRING
        6, 7, 8 -> SUMMER
        9, 10, 11 -> AUTUMN
        else -> throw IllegalArgumentException("incorrect selectedMonthValue: $selectedMonthValue")
    }
}

fun getBackgroundGradient(viewShown: ViewShown, colorScheme: ColorScheme): Brush {
    return when (viewShown) {
        ViewShown.MONTH, ViewShown.YEAR -> getMonthAndYearViewBackgroundGradient(colorScheme)
        ViewShown.DAY, ViewShown.SETTINGS -> getDefaultBackgroundGradient(colorScheme)
    }
}

fun getDefaultBackgroundGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colors = listOf(
        colorScheme.viewsTop,
        colorScheme.viewsBottom
    )
)

fun getMonthAndYearViewBackgroundGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.viewsTop,
        0.12f to colorScheme.viewsTop,
        0.27f to colorScheme.viewsBottom,
        1f to colorScheme.viewsBottom
    )
)

fun getYearViewInnerBackgroundGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.yearViewBtnTop,
        0.2f to colorScheme.yearViewBtnBottom,
        1f to colorScheme.viewsBottom
    )
)

fun getYearViewButtonGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colors = listOf(
        colorScheme.yearViewBtnTop,
        colorScheme.yearViewBtnBottom
    )
)

fun getNoteButtonGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colors = listOf(
        colorScheme.text,
        colorScheme.text.withAlpha(0.67f)
    )
)

fun getNoteEditGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colors = listOf(
        colorScheme.text,
        colorScheme.text.withAlpha(0.8f)
    )
)

fun getNoteViewGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colors = listOf(
        colorScheme.text.withAlpha(0.85f),
        colorScheme.text.withAlpha(0.5f)
    )
)

fun Color.withAlpha(alpha: Float) = Color(
    red = this.red,
    green = this.green,
    blue = this.blue,
    alpha = alpha
)
