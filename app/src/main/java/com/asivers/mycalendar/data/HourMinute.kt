package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class HourMinute(
    val hour: Int,
    val minute: Int
)
