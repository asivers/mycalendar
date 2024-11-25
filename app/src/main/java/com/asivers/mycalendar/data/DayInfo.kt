package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class DayInfo(
    val holiday: LocalString? = null,
    val notHoliday: LocalString? = null,
    val notes: List<NoteInfo> = listOf()
)
