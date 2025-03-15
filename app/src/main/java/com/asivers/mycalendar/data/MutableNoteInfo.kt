package com.asivers.mycalendar.data

data class MutableNoteInfo(
    var id: Int? = null,
    var msg: String = "",
    var isEveryYear: Boolean = false,
    var notificationTime: NotificationTime? = null,
    var changed: Boolean = false
) {
    constructor(noteInfo: NoteInfo): this(
        id = noteInfo.id,
        msg = noteInfo.msg,
        isEveryYear = noteInfo.forYear == null,
        notificationTime = noteInfo.notificationTime
    )

    fun refreshMsg(newMsg: String): MutableNoteInfo {
        return MutableNoteInfo(
            id = id,
            msg = newMsg,
            isEveryYear = isEveryYear,
            notificationTime = notificationTime,
            changed = true
        )
    }

    fun refreshIsEveryYear(newIsEveryYear: Boolean): MutableNoteInfo {
        return MutableNoteInfo(
            id = id,
            msg = msg,
            isEveryYear = newIsEveryYear,
            notificationTime = notificationTime,
            changed = true
        )
    }

    fun refreshNotificationTime(newNotificationTime: NotificationTime?): MutableNoteInfo {
        return MutableNoteInfo(
            id = id,
            msg = msg,
            isEveryYear = isEveryYear,
            notificationTime = newNotificationTime,
            changed = true
        )
    }
}
