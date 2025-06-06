package com.asivers.mycalendar.enums

import com.asivers.mycalendar.constants.schemes.AUTUMN
import com.asivers.mycalendar.constants.schemes.EXPERIMENTAL
import com.asivers.mycalendar.constants.schemes.LIGHT
import com.asivers.mycalendar.constants.schemes.SPRING
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.constants.schemes.WINTER
import com.asivers.mycalendar.data.scheme.ColorScheme

enum class UserTheme(
    val colorScheme: ColorScheme?,
    override val translationKey: String
): SettingsItem {
    CHANGE_BY_SEASON(
        colorScheme = null,
        translationKey = "changeBySeason"
    ),
    THEME_SUMMER(
        colorScheme = SUMMER,
        translationKey = "summer"
    ),
    THEME_AUTUMN(
        colorScheme = AUTUMN,
        translationKey = "autumn"
    ),
    THEME_WINTER(
        colorScheme = WINTER,
        translationKey = "winter"
    ),
    THEME_SPRING(
        colorScheme = SPRING,
        translationKey = "spring"
    ),
    THEME_LIGHT(
        colorScheme = LIGHT,
        translationKey = "light"
    ),
    THEME_EXPERIMENTAL(
        colorScheme = EXPERIMENTAL,
        translationKey = "experimental"
    )
}
