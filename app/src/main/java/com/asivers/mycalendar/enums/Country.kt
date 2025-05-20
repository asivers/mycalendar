package com.asivers.mycalendar.enums

enum class Country(
    val assetName: String?,
    override val translationKey: String
): SettingsItem {
    NO_DISPLAY(
        assetName = null,
        translationKey = "noDisplay"
    ),
    FRANCE(
        assetName = "france",
        translationKey = "france"
    ),
    RUSSIA(
        assetName = "russia",
        translationKey = "russia"
    ),
    SPAIN(
        assetName = "spain",
        translationKey = "spain"
    )
}
