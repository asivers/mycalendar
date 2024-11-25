package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class HourMinute(
    val hour: Int,
    val minute: Int
): Comparable<HourMinute> {
    override fun compareTo(other: HourMinute) = compareValuesBy(this, other,
        { it.hour },
        { it.minute }
    )
    override fun toString(): String {
        val fullHour = hour.toString().padStart(2, '0')
        val fullMinute = minute.toString().padStart(2, '0')
        return "$fullHour:$fullMinute"
    }
}
