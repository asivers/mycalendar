package com.asivers.mycalendar.utils

import com.asivers.mycalendar.data.scheme.TranslationScheme
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.SettingsEnum
import com.asivers.mycalendar.enums.UserTheme
import com.asivers.mycalendar.enums.WeekendMode

fun getExistingLocaleForLanguage(language: String): ExistingLocale {
    return enumValues<ExistingLocale>()
        .find { language == it.assetName }
        ?: ExistingLocale.EN
}

fun getTranslatedSettingsParamName(
    items: List<SettingsEnum>,
    translationScheme: TranslationScheme
): String {
    if (items.isEmpty()) {
        return ""
    }
    return when (items[0]) {
        is Country -> translationScheme.countriesParam
        is ExistingLocale -> translationScheme.localesParam
        is UserTheme -> translationScheme.themesParam
        is WeekendMode -> translationScheme.weekendModesParam
    }
}

fun getTranslatedSettingsItemName(
    item: SettingsEnum,
    translationScheme: TranslationScheme
): String {
    val translationSchemeMap = when (item) {
        is Country -> translationScheme.countries
        is ExistingLocale -> translationScheme.locales
        is UserTheme -> translationScheme.themes
        is WeekendMode -> translationScheme.weekendModes
    }
    return translationSchemeMap[item.translationKey]
        ?: item.translationKey.replaceFirstChar(Char::titlecase)
}

fun getTranslatedSettingsItemsNames(
    items: List<SettingsEnum>,
    translationScheme: TranslationScheme
): List<String> {
    if (items.isEmpty()) {
        return listOf()
    }
    val translationSchemeMap = when (items[0]) {
        is Country -> translationScheme.countries
        is ExistingLocale -> translationScheme.locales
        is UserTheme -> translationScheme.themes
        is WeekendMode -> translationScheme.weekendModes
    }
    return items.map {
        translationSchemeMap[it.translationKey]
            ?: it.translationKey.replaceFirstChar(Char::titlecase)
    }
}
