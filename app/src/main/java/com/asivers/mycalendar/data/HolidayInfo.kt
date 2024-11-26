package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class HolidayInfo(
    val en: String,
    val es: String? = null,
    val ru: String? = null
)
