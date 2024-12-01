package com.asivers.mycalendar.utils

import android.content.Context
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.data.scheme.CountryHolidayScheme
import com.asivers.mycalendar.data.scheme.TranslationScheme
import com.asivers.mycalendar.enums.ExistingLocale
import kotlinx.serialization.json.Json
import java.io.File

fun getHolidaySchemeForCountry(country: Country, ctx: Context): CountryHolidayScheme {
    if (country == Country.NO_DISPLAY) {
        return CountryHolidayScheme(mapOf(), mapOf())
    }
    val fileName = "holidays" + File.separator + country.assetName + ".json"
    val json: String = ctx.assets.open(fileName).bufferedReader().use { it.readText() }
    return Json.decodeFromString<CountryHolidayScheme>(json)
}

fun getTranslationSchemeForExistingLocale(existingLocale: ExistingLocale, ctx: Context): TranslationScheme {
    val fileName = "translations" + File.separator + existingLocale.assetName + ".json"
    val json: String = ctx.assets.open(fileName).bufferedReader().use { it.readText() }
    return Json.decodeFromString<TranslationScheme>(json)
}
