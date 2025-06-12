package com.asivers.mycalendar.utils.proto

import android.content.Context
import com.asivers.mycalendar.data.proto.SavedSettingsOuterClass.*
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.NotificationsMode
import com.asivers.mycalendar.enums.SettingsItem
import com.asivers.mycalendar.enums.SettingsParam
import com.asivers.mycalendar.enums.UserTheme
import com.asivers.mycalendar.enums.WeekNumbersMode
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.serializers.savedSettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

fun getSavedSettings(ctx: Context): SavedSettings {
    return runBlocking { ctx.savedSettingsDataStore.data.first() }
}

fun getSavedCountry(
    savedSettings: SavedSettings,
    systemLocale: Locale,
    ctx: Context
): Country {
    if (savedSettings.country.isEmpty()) {
        val countryToSet = enumValues<Country>()
            .find { systemLocale.country == it.systemLocaleCountry }
            ?: Country.NO_DISPLAY
        updateOneSetting(SettingsParam.COUNTRY, countryToSet, ctx)
        return countryToSet
    }
    return Country.valueOf(savedSettings.country)
}

fun getSavedLocale(
    savedSettings: SavedSettings,
    systemLocale: Locale,
    ctx: Context
): ExistingLocale {
    if (savedSettings.locale.isEmpty()) {
        val localeToSet = enumValues<ExistingLocale>()
            .find { systemLocale.language == it.assetName }
            ?: ExistingLocale.EN
        updateOneSetting(SettingsParam.EXISTING_LOCALE, localeToSet, ctx)
        return localeToSet
    }
    return ExistingLocale.valueOf(savedSettings.locale)
}

fun getSavedTheme(savedSettings: SavedSettings): UserTheme {
    return if (savedSettings.theme.isEmpty())
        UserTheme.CHANGE_BY_SEASON
    else
        UserTheme.valueOf(savedSettings.theme)
}

fun getSavedWeekendMode(savedSettings: SavedSettings): WeekendMode {
    return if (savedSettings.weekendMode.isEmpty())
        WeekendMode.SATURDAY_SUNDAY
    else
        WeekendMode.valueOf(savedSettings.weekendMode)
}

fun getSavedWeekNumbersMode(savedSettings: SavedSettings): WeekNumbersMode {
    return if (savedSettings.weekNumbersMode.isEmpty())
        WeekNumbersMode.OFF
    else
        WeekNumbersMode.valueOf(savedSettings.weekNumbersMode)
}

fun getSavedNotificationsMode(savedSettings: SavedSettings): NotificationsMode {
    return if (savedSettings.notificationsMode.isEmpty())
        NotificationsMode.WITH_RINGTONE
    else
        NotificationsMode.valueOf(savedSettings.notificationsMode)
}

fun updateOneSetting(
    param: SettingsParam,
    item: SettingsItem,
    ctx: Context
) {
    runBlocking {
        ctx.savedSettingsDataStore.updateData { currentSavedSettings ->
            val builder = currentSavedSettings.toBuilder()
            val value = item.toString()
            when (param) {
                SettingsParam.COUNTRY -> builder.setCountry(value)
                SettingsParam.EXISTING_LOCALE -> builder.setLocale(value)
                SettingsParam.USER_THEME -> builder.setTheme(value)
                SettingsParam.WEEKEND_MODE -> builder.setWeekendMode(value)
                SettingsParam.WEEK_NUMBERS_MODE -> builder.setWeekNumbersMode(value)
                SettingsParam.NOTIFICATIONS_MODE -> builder.setNotificationsMode(value)
            }
            builder.build()
        }
    }
}
