package com.asivers.mycalendar.enums

enum class ExistingLocale(
    val assetName: String,
    override val translationKey: String
): SettingsItem {
    EN(
        assetName = "en",
        translationKey = "en"
    ),
    ES(
        assetName = "es",
        translationKey = "es"
    ),
    RU(
        assetName = "ru",
        translationKey = "ru"
    )
}
