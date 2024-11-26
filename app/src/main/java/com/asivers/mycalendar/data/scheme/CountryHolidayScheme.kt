package com.asivers.mycalendar.data.scheme

import com.asivers.mycalendar.data.DayInfo
import kotlinx.serialization.Serializable

@Serializable
data class CountryHolidayScheme(
    val everyYear: Map<Int, Map<Int, DayInfo>>,
    val oneTime: Map<Int, Map<Int, Map<Int, DayInfo>>>
)
