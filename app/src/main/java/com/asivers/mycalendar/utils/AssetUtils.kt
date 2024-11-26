package com.asivers.mycalendar.utils

import android.content.Context
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.data.scheme.CountryHolidaysScheme
import com.asivers.mycalendar.data.scheme.TranslationsScheme
import com.asivers.mycalendar.enums.ExistingLocale
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

fun getHolidaysSchemeForCountry(country: Country?, ctx: Context): CountryHolidaysScheme {
    if (country == null) {
        return CountryHolidaysScheme(mapOf(), mapOf())
    }
    val fileName = "holidays" + File.separator + country.assetName + ".json"
    val json: String = ctx.assets.open(fileName).bufferedReader().use { it.readText() }
    return Json.decodeFromString<CountryHolidaysScheme>(json)
}

fun getTranslationsSchemeForExistingLocale(existingLocale: ExistingLocale, ctx: Context): TranslationsScheme {
    val fileName = "translations" + File.separator + existingLocale.assetName + ".json"
    val json: String = ctx.assets.open(fileName).bufferedReader().use { it.readText() }
    return Json.decodeFromString<TranslationsScheme>(json)
}
