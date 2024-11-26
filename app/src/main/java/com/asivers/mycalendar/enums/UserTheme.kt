package com.asivers.mycalendar.enums

import com.asivers.mycalendar.constants.schemes.AUTUMN
import com.asivers.mycalendar.constants.schemes.SPRING
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.constants.schemes.WINTER
import com.asivers.mycalendar.data.scheme.ColorScheme

enum class UserTheme(val colorScheme: ColorScheme?) {
    CHANGE_BY_SEASON(null),
    THEME_SUMMER(SUMMER),
    THEME_AUTUMN(AUTUMN),
    THEME_WINTER(WINTER),
    THEME_SPRING(SPRING)
}
