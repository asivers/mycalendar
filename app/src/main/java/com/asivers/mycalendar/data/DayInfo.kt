package com.asivers.mycalendar.data

import com.asivers.mycalendar.enums.DayType
import kotlinx.serialization.Serializable

@Serializable
data class DayInfo(
    val msg: String,
    val type: DayType = DayType.HOLIDAY,
    val time: HourMinute? = null
)
