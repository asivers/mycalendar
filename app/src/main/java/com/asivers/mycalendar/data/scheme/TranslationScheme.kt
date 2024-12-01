package com.asivers.mycalendar.data.scheme

import kotlinx.serialization.Serializable

@Serializable
data class TranslationScheme(
    val yearView: String,
    val months: List<String>,
    val daysOfWeek3: List<String>,
    val daysOfWeek1: List<String>,
    val countriesParam: String,
    val countries: Map<String, String>,
    val localesParam: String,
    val locales: Map<String, String>,
    val themesParam: String,
    val themes: Map<String, String>,
    val weekendModesParam: String,
    val weekendModes: Map<String, String>
)
