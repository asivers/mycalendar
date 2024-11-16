package com.asivers.mycalendar.ui.theme.custom

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@JvmInline
value class CustomColor(val value: ULong) {
    companion object {
        @Stable val TRANSPARENT = Color(0x00FFFFFF)
        @Stable val BLACK = Color(0xFF000000)
        @Stable val WHITE = Color(0xFFFFFFFF)
        @Stable val MV_GRADIENT_TOP = Color(0xFF5DA493)
        @Stable val MV_GRADIENT_BOTTOM = Color(0xFF337623)
        @Stable val MYV_GREEN_DAY_HOLIDAY = Color(0xFF92D700)
        @Stable val MV_GRADIENT_YEAR_VIEW_BUTTON_TOP = Color(0xFF92D700)
        @Stable val MV_GRADIENT_YEAR_VIEW_BUTTON_BOTTOM = Color(0xFF5B9F14)
        @Stable val YV_GRADIENT_TOP = Color(0xFF91D600)
        @Stable val YV_GRADIENT_CENTER = Color(0xFF5CA013)
        @Stable val YV_GRADIENT_BOTTOM = Color(0xFF337623)
        @Stable val YV_CURRENT_MONTH = Color(0xFF5DA493)
    }
}
