package com.asivers.mycalendar.enums

enum class ExistingLocale(
    val assetName: String,
    override val translationKey: String
): SettingsItem {
    EN(
        assetName = "en",
        translationKey = "en"
    ),
    DE(
        assetName = "de",
        translationKey = "de"
    ),
    ES(
        assetName = "es",
        translationKey = "es"
    ),
    FR(
        assetName = "fr",
        translationKey = "fr"
    ),
    IT(
        assetName = "it",
        translationKey = "it"
    ),
    PT(
        assetName = "pt",
        translationKey = "pt"
    ),
    TR(
        assetName = "tr",
        translationKey = "tr"
    ),
    RU(
        assetName = "ru",
        translationKey = "ru"
    ),
    SR(
        assetName = "sr",
        translationKey = "sr"
    )
}
