package com.example.mycalendar.constants

import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Brush
import com.example.mycalendar.ui.theme.custom.CustomColor

val TRANSPARENT_BUTTON_COLORS: ButtonColors = ButtonColors(
    CustomColor.TRANSPARENT,
    CustomColor.TRANSPARENT,
    CustomColor.TRANSPARENT,
    CustomColor.TRANSPARENT
)

val MONTH_VIEW_BACKGROUND_GRADIENT: Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to CustomColor.MV_GRADIENT_TOP,
        0.1f to CustomColor.MV_GRADIENT_TOP,
        0.25f to CustomColor.MV_GRADIENT_BOTTOM,
        1f to CustomColor.MV_GRADIENT_BOTTOM,
    )
)
