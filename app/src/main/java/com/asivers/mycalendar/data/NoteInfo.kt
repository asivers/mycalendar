package com.asivers.mycalendar.data

import com.asivers.mycalendar.data.proto.SavedNotesOuterClass
import kotlinx.serialization.Serializable

@Serializable
data class NoteInfo(
    val id: Int,
    val msg: String,
    val forYear: Int?,
    val isHoliday: Boolean,
    val notificationTime: NotificationTime?
): Comparable<NoteInfo> {

    constructor(
        protoNote: SavedNotesOuterClass.SavedNotes.Note
    ): this(
        id = protoNote.id,
        msg = protoNote.msg,
        forYear = if (protoNote.forYear > 0) protoNote.forYear else null,
        isHoliday = protoNote.isHoliday,
        notificationTime = NotificationTime(protoNote.notificationTime)
    )

    override fun compareTo(other: NoteInfo) = compareValuesBy(this, other,
        { it.notificationTime },
        { it.id }
    )
}
