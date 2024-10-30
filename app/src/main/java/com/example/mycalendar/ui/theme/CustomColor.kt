package com.example.mycalendar.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@JvmInline
value class CustomColor(val value: ULong) {
    companion object {
        @Stable val Transparent = Color(0x00FFFFFF)
        @Stable val Black = Color(0xFF000000)
        @Stable val White = Color(0xFFFFFFFF)
        @Stable val Mv_gradient_top = Color(0xFF5DA493)
        @Stable val Mv_gradient_bottom = Color(0xFF337623)
        @Stable val Myv_green_day_holiday = Color(0xFF92D700)
        @Stable val Mv_gradient_year_view_button_top = Color(0xFF92D700)
        @Stable val Mv_gradient_year_view_button_bottom = Color(0xFF5B9F14)
        @Stable val Yv_gradient_top = Color(0xFF91D600)
        @Stable val Yv_gradient_center = Color(0xFF5CA013)
        @Stable val Yv_gradient_bottom = Color(0xFF337623)
        @Stable val Yv_current_month = Color(0xFF5DA493)
    }
}
