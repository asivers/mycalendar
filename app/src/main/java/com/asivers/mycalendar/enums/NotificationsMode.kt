package com.asivers.mycalendar.enums

enum class NotificationsMode(
    override val translationKey: String
): SettingsItem {
    WITH_RINGTONE(
        translationKey = "withRingtone"
    ),
    WITHOUT_RINGTONE(
        translationKey = "withoutRingtone"
    )
}
