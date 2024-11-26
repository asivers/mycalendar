package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class DayInfo(
    val holiday: HolidayInfo? = null,
    val notHoliday: HolidayInfo? = null,
    val notes: List<NoteInfo> = listOf()
)
