package com.asivers.mycalendar.data

import com.asivers.mycalendar.data.proto.SavedNotesOuterClass
import kotlinx.serialization.Serializable

@Serializable
data class NoteInfo(
    val id: Int,
    val msg: String,
    val isEveryYear: Boolean,
    val notificationTime: NotificationTime?
): Comparable<NoteInfo> {

    constructor(
        protoNote: SavedNotesOuterClass.SavedNotes.Note
    ): this(
        id = protoNote.id,
        msg = protoNote.msg,
        isEveryYear = protoNote.forYear == 0,
        notificationTime = if (protoNote.notificationTimeNull)
            null else NotificationTime(protoNote.notificationTimeValue)
    )

    override fun compareTo(other: NoteInfo) = compareValuesBy(this, other,
        { it.notificationTime },
        { it.id }
    )
}
