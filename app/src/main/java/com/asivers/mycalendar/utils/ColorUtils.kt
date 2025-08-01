package com.asivers.mycalendar.utils

import android.os.Build
import android.view.Window
import android.view.WindowInsets
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowInsetsControllerCompat
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
        colorScheme.inputNoteBackground,
        colorScheme.inputNoteBackground.multiplyAlpha(0.67f)
    )
)

fun getNoteEditGradient(colorScheme: ColorScheme): Brush = Brush.verticalGradient(
    colors = listOf(
        colorScheme.inputNoteBackground,
        colorScheme.inputNoteBackground.multiplyAlpha(0.8f)
    )
)

fun getNoteViewGradient(colorScheme: ColorScheme): Brush {
    val isExperimental = colorScheme.id == "EXPERIMENTAL"
    return Brush.verticalGradient(
        colors = listOf(
            colorScheme.inputNoteBackground.multiplyAlpha(if (isExperimental) 0.95f else 0.85f),
            colorScheme.inputNoteBackground.multiplyAlpha(if (isExperimental) 0.8f else 0.5f)
        )
    )
}

fun getExistingNoteBackgroundAlpha(colorScheme: ColorScheme): Float {
    if (colorScheme.id == "LIGHT") return 0.6f
    if (colorScheme.id == "EXPERIMENTAL") return 0.1f
    return 0.2f
}

fun Color.multiplyAlpha(alpha: Float) = Color(
    red = this.red,
    green = this.green,
    blue = this.blue,
    alpha = this.alpha * alpha
)

fun makeNavigationBarBlack(window: Window) {
    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            val navigationBarInsets = insets.getInsets(WindowInsets.Type.navigationBars())
            view.setPadding(0, 0, 0, navigationBarInsets.bottom)
            view.setBackgroundColor(android.graphics.Color.BLACK)
            insets
        }
    } else {
        window.navigationBarColor = android.graphics.Color.BLACK
    }
}
