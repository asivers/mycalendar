package com.asivers.mycalendar.utils

import com.asivers.mycalendar.data.HolidayInfo
import com.asivers.mycalendar.data.scheme.TranslationScheme
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.SettingsItem
import com.asivers.mycalendar.enums.SettingsParam
import com.asivers.mycalendar.enums.UserTheme
import com.asivers.mycalendar.enums.WeekendMode

fun getTranslatedSettingsParamName(
    param: SettingsParam,
    translationScheme: TranslationScheme
): String {
    return when (param) {
        SettingsParam.COUNTRY -> translationScheme.countriesParam
        SettingsParam.EXISTING_LOCALE -> translationScheme.localesParam
        SettingsParam.USER_THEME -> translationScheme.themesParam
        SettingsParam.WEEKEND_MODE -> translationScheme.weekendModesParam
    }
}

fun getTranslatedSettingsItemName(
    item: SettingsItem,
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

fun <T : SettingsItem> getTranslatedSettingsItemsNames(
    items: List<T>,
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
        else -> throw IllegalArgumentException() // will never happen
    }
    return items.map {
        translationSchemeMap[it.translationKey]
            ?: it.translationKey.replaceFirstChar(Char::titlecase)
    }
}

fun HolidayInfo.translateHolidayInfo(locale: ExistingLocale): String {
    val translatedHolidayInfo = when (locale) {
        ExistingLocale.EN -> this.en
        ExistingLocale.RU -> this.ru
    }
    return translatedHolidayInfo ?: this.en
}
