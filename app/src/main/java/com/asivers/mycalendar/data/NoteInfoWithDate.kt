package com.asivers.mycalendar.data

data class NoteInfoWithDate(
    val noteInfo: NoteInfo,
    val year: Int?,
    val monthValue: Int,
    val dayOfMonth: Int
)
