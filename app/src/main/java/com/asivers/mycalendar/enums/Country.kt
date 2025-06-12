package com.asivers.mycalendar.enums

enum class Country(
    val assetName: String?,
    override val translationKey: String,
    val systemLocaleCountry: String?
): SettingsItem {
    NO_DISPLAY(
        assetName = null,
        translationKey = "noDisplay",
        systemLocaleCountry = null
    ),
    ARGENTINA(
        assetName = "argentina",
        translationKey = "argentina",
        systemLocaleCountry = "AR"
    ),
    BRAZIL(
        assetName = "brazil",
        translationKey = "brazil",
        systemLocaleCountry = "BR"
    ),
    CANADA(
        assetName = "canada",
        translationKey = "canada",
        systemLocaleCountry = "CA"
    ),
    ENGLAND(
        assetName = "england",
        translationKey = "england",
        systemLocaleCountry = "GB"
    ),
    FRANCE(
        assetName = "france",
        translationKey = "france",
        systemLocaleCountry = "FR"
    ),
    GERMANY(
        assetName = "germany",
        translationKey = "germany",
        systemLocaleCountry = "DE"
    ),
    INDIA(
        assetName = "india",
        translationKey = "india",
        systemLocaleCountry = "IN"
    ),
    ITALY(
        assetName = "italy",
        translationKey = "italy",
        systemLocaleCountry = "IT"
    ),
    MEXICO(
        assetName = "mexico",
        translationKey = "mexico",
        systemLocaleCountry = "MX"
    ),
    RUSSIA(
        assetName = "russia",
        translationKey = "russia",
        systemLocaleCountry = "RU"
    ),
    SERBIA(
        assetName = "serbia",
        translationKey = "serbia",
        systemLocaleCountry = "RS"
    ),
    SPAIN(
        assetName = "spain",
        translationKey = "spain",
        systemLocaleCountry = "ES"
    ),
    TURKEY(
        assetName = "turkey",
        translationKey = "turkey",
        systemLocaleCountry = "TR"
    ),
    USA(
        assetName = "usa",
        translationKey = "usa",
        systemLocaleCountry = "US"
    )
}
