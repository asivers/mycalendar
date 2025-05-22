package com.asivers.mycalendar.data.scheme

import com.asivers.mycalendar.data.HolidayInfo
import kotlinx.serialization.Serializable

@Serializable
data class CountryHolidayScheme(
    val everyYear: Map<Int, Map<Int, HolidayInfo>>,
    val oneTime: Map<Int, Map<Int, Map<Int, HolidayInfo>>>
)
