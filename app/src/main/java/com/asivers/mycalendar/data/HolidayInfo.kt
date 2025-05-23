package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class HolidayInfo(
    val en: String,
    val de: String? = null,
    val es: String? = null,
    val fr: String? = null,
    val it: String? = null,
    val pt: String? = null,
    val tr: String? = null,
    val ru: String? = null,
    val sr: String? = null,
    val notHoliday: Boolean = false
)
