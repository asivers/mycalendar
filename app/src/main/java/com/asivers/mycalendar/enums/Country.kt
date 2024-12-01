package com.asivers.mycalendar.enums

enum class Country(
    val assetName: String?,
    override val translationKey: String
): SettingsItem {
    NO_DISPLAY(
        assetName = null,
        translationKey = "noDisplay"
    ),
    RUSSIA(
        assetName = "russia",
        translationKey = "russia"
    ),
    SPAIN(
        assetName = "spain",
        translationKey = "spain"
    ),
    SPAIN_REUS(
        assetName = "spain_reus",
        translationKey = "spain_reus" // actually not existing, to delete
    )
}
