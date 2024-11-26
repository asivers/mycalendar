package com.asivers.mycalendar.data.scheme

import kotlinx.serialization.Serializable

@Serializable
data class TranslationScheme(
    val yearView: String,
    val months: List<String>,
    val daysOfWeek3: List<String>,
    val daysOfWeek1: List<String>
)
