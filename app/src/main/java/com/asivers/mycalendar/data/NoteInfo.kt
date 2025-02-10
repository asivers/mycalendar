package com.asivers.mycalendar.data

import com.asivers.mycalendar.data.proto.SavedNotesOuterClass
import kotlinx.serialization.Serializable

@Serializable
data class NoteInfo(
    val id: Int,
    val msg: String,
    val forYear: Int?,
    val notificationTime: NotificationTime?,
    val isHoliday: Boolean = false
): Comparable<NoteInfo> {

    constructor(
        protoNote: SavedNotesOuterClass.SavedNotes.Note
    ): this(
        id = protoNote.id,
        msg = protoNote.msg,
        forYear = if (protoNote.forYear > 0) protoNote.forYear else null,
        notificationTime = NotificationTime(protoNote.notificationTime),
        isHoliday = protoNote.isHoliday
    )

    override fun compareTo(other: NoteInfo) = compareValuesBy(this, other,
        { it.notificationTime },
        { it.id }
    )
}
