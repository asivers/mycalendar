package com.asivers.mycalendar.utils

import android.content.Context
import com.asivers.mycalendar.R
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

fun getYearIconId(year: Int): Int {
    return when (year % 12) {
        0 -> R.drawable.year_monkey
        1 -> R.drawable.year_rooster
        2 -> R.drawable.year_dog
        3 -> R.drawable.year_pig
        4 -> R.drawable.year_rat
        5 -> R.drawable.year_ox
        6 -> R.drawable.year_tiger
        7 -> R.drawable.year_rabbit
        8 -> R.drawable.year_dragon
        9 -> R.drawable.year_snake
        10 -> R.drawable.year_horse
        else -> R.drawable.year_goat
    }
}
