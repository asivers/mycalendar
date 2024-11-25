package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class DayInfo(
    val holiday: String? = null,
    val notHoliday: String? = null,
    val notes: List<NoteInfo> = listOf()
)
