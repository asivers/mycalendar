package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class NoteInfo(
    val msg: String,
    val time: HourMinute? = null,
    val order: Int
): Comparable<NoteInfo> {
    override fun compareTo(other: NoteInfo) = compareValuesBy(this, other,
        { it.time },
        { it.order }
    )
}
