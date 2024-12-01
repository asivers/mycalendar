package com.asivers.mycalendar.enums

enum class ExistingLocale(
    val assetName: String,
    override val translationKey: String
): SettingsItem {
    EN(
        assetName = "en",
        translationKey = "en"
    ),
    RU(
        assetName = "ru",
        translationKey = "ru"
    )
}
