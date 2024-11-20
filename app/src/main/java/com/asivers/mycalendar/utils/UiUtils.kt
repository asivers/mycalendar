package com.asivers.mycalendar.utils

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.asivers.mycalendar.ui.theme.custom.CustomColorScheme
import com.asivers.mycalendar.ui.theme.custom.autumnColorScheme
import com.asivers.mycalendar.ui.theme.custom.springColorScheme
import com.asivers.mycalendar.ui.theme.custom.summerColorScheme
import com.asivers.mycalendar.ui.theme.custom.winterColorScheme

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = this.clickable(
    interactionSource = null,
    indication = null
) {
    onClick()
}

fun getColorScheme(selectedMonthIndex: Int): CustomColorScheme {
    return when (selectedMonthIndex) {
        11, 0, 1 -> winterColorScheme
        2, 3, 4 -> springColorScheme
        5, 6, 7 -> summerColorScheme
        8, 9, 10 -> autumnColorScheme
        else -> throw IllegalArgumentException("incorrect selectedMonthIndex: $selectedMonthIndex")
    }
}

fun getMonthViewBackgroundGradient(colorScheme: CustomColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.mvLight,
        0.1f to colorScheme.mvLight,
        0.25f to colorScheme.myvDark,
        1f to colorScheme.myvDark,
    )
)

fun getYearViewBackgroundGradient(colorScheme: CustomColorScheme): Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to colorScheme.yvLight,
        0.2f to colorScheme.yvMedium,
        1f to colorScheme.myvDark,
    )
)
