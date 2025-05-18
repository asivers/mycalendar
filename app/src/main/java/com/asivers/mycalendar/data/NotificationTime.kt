package com.asivers.mycalendar.data

import com.asivers.mycalendar.data.proto.SavedNotesOuterClass
import kotlinx.serialization.Serializable
import java.time.LocalTime

@Serializable
data class NotificationTime(
    val hour: Int,
    val minute: Int
): Comparable<NotificationTime> {

    constructor(localTime: LocalTime): this(
        hour = localTime.hour,
        minute = localTime.minute
    )

    constructor(
        protoNotificationTime: SavedNotesOuterClass.SavedNotes.NotificationTime
    ): this(
        hour = protoNotificationTime.hour,
        minute = protoNotificationTime.minute
    )

    override fun compareTo(other: NotificationTime) = compareValuesBy(this, other,
        { it.hour },
        { it.minute }
    )

    override fun toString(): String {
        val fullHour = hour.toString().padStart(2, '0')
        val fullMinute = minute.toString().padStart(2, '0')
        return "$fullHour:$fullMinute"
    }
}
