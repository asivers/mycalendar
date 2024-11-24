package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class HolidaysForCountry(
    val everyYear: Map<Int, Map<Int, DayInfo>>,
    val oneTime: Map<Int, Map<Int, Map<Int, DayInfo>>>
)
