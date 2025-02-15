package com.asivers.mycalendar.enums

enum class WeekNumbersMode(
    override val translationKey: String
): SettingsItem {
    OFF(
        translationKey = "off"
    ),
    ON(
        translationKey = "on"
    )
}
