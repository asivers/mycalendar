package com.asivers.mycalendar.utils

import android.content.Context
import com.asivers.mycalendar.constants.Country
import com.asivers.mycalendar.data.HolidaysForCountry
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun getHolidaysForCountry(country: Country?, ctx: Context): HolidaysForCountry {
    if (country == null) {
        return HolidaysForCountry(mapOf(), mapOf())
    }
    val json: String = ctx.assets.open(country.jsonAsset).bufferedReader().use { it.readText() }
    return Json.decodeFromString<HolidaysForCountry>(json)
}
