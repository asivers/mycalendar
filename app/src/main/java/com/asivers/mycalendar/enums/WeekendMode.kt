package com.asivers.mycalendar.enums

enum class WeekendMode(
    override val translationKey: String
): SettingsEnum {
    SATURDAY_SUNDAY(
        translationKey = "saturdaySunday"
    ),
    ONLY_SUNDAY(
        translationKey = "onlySunday"
    ),
    NO_DISPLAY(
        translationKey = "noDisplay"
    )
}
