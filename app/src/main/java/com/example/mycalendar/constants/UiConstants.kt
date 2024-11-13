package com.example.mycalendar.constants

import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Brush
import com.example.mycalendar.ui.theme.CustomColor

val TRANSPARENT_BUTTON_COLORS: ButtonColors = ButtonColors(
    CustomColor.Transparent,
    CustomColor.Transparent,
    CustomColor.Transparent,
    CustomColor.Transparent
)

val MONTH_VIEW_BACKGROUND_GRADIENT: Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to CustomColor.Mv_gradient_top,
        0.1f to CustomColor.Mv_gradient_top,
        0.25f to CustomColor.Mv_gradient_bottom,
        1f to CustomColor.Mv_gradient_bottom,
    )
)
