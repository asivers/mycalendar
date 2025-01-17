package com.asivers.mycalendar.utils.proto

import android.content.Context
import com.asivers.mycalendar.data.proto.SavedSettingsOuterClass
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.SettingsItem
import com.asivers.mycalendar.enums.SettingsParam
import com.asivers.mycalendar.enums.UserTheme
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.serializers.savedSettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

fun getSavedSettings(ctx: Context): SavedSettingsOuterClass.SavedSettings {
    return runBlocking { ctx.savedSettingsDataStore.data.first() }
}

fun getSavedCountry(savedSettings: SavedSettingsOuterClass.SavedSettings): Country {
    return if (savedSettings.country.isEmpty())
        Country.NO_DISPLAY
    else
        Country.valueOf(savedSettings.country)
}

fun getSavedTheme(savedSettings: SavedSettingsOuterClass.SavedSettings): UserTheme {
    return if (savedSettings.theme.isEmpty())
        UserTheme.CHANGE_BY_SEASON
    else
        UserTheme.valueOf(savedSettings.theme)
}

fun getSavedWeekendMode(savedSettings: SavedSettingsOuterClass.SavedSettings): WeekendMode {
    return if (savedSettings.weekendMode.isEmpty())
        WeekendMode.SATURDAY_SUNDAY
    else
        WeekendMode.valueOf(savedSettings.weekendMode)
}

fun getSavedLocale(
    savedSettings: SavedSettingsOuterClass.SavedSettings,
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
            }
            builder.build()
        }
    }
}
