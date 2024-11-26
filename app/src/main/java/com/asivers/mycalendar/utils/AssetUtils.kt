package com.asivers.mycalendar.utils

import android.content.Context
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.data.HolidaysForCountry
import com.asivers.mycalendar.data.scheme.TranslationsScheme
import com.asivers.mycalendar.enums.ExistingLocale
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

fun getHolidaysForCountry(country: Country?, ctx: Context): HolidaysForCountry {
    if (country == null) {
        return HolidaysForCountry(mapOf(), mapOf())
    }
    val fileName = "holidays" + File.separator + country.assetName + ".json"
    val json: String = ctx.assets.open(fileName).bufferedReader().use { it.readText() }
    return Json.decodeFromString<HolidaysForCountry>(json)
}

fun getTranslationsForExistingLocale(existingLocale: ExistingLocale, ctx: Context): TranslationsScheme {
    val fileName = "translations" + File.separator + existingLocale.assetName + ".json"
    val json: String = ctx.assets.open(fileName).bufferedReader().use { it.readText() }
    return Json.decodeFromString<TranslationsScheme>(json)
}
