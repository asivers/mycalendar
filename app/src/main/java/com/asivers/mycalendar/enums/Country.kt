package com.asivers.mycalendar.enums

enum class Country(
    val assetName: String?,
    override val translationKey: String
): SettingsItem {
    NO_DISPLAY(
        assetName = null,
        translationKey = "noDisplay"
    ),
    ARGENTINA(
        assetName = "argentina",
        translationKey = "argentina"
    ),
    BRAZIL(
        assetName = "brazil",
        translationKey = "brazil"
    ),
    CANADA(
        assetName = "canada",
        translationKey = "canada"
    ),
    ENGLAND(
        assetName = "england",
        translationKey = "england"
    ),
    FRANCE(
        assetName = "france",
        translationKey = "france"
    ),
    GERMANY(
        assetName = "germany",
        translationKey = "germany"
    ),
    INDIA(
        assetName = "india",
        translationKey = "india"
    ),
    ITALY(
        assetName = "italy",
        translationKey = "italy"
    ),
    MEXICO(
        assetName = "mexico",
        translationKey = "mexico"
    ),
    RUSSIA(
        assetName = "russia",
        translationKey = "russia"
    ),
    SERBIA(
        assetName = "serbia",
        translationKey = "serbia"
    ),
    SPAIN(
        assetName = "spain",
        translationKey = "spain"
    ),
    TURKEY(
        assetName = "turkey",
        translationKey = "turkey"
    ),
    USA(
        assetName = "usa",
        translationKey = "usa"
    )
}
