package com.asivers.mycalendar.data.scheme

import com.asivers.mycalendar.data.DayTextInfo
import kotlinx.serialization.Serializable

@Serializable
data class CountryHolidayScheme(
    val everyYear: Map<Int, Map<Int, DayTextInfo>>,
    val oneTime: Map<Int, Map<Int, Map<Int, DayTextInfo>>>
)
