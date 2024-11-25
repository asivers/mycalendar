package com.asivers.mycalendar.data

import kotlinx.serialization.Serializable

@Serializable
data class NoteInfo(
    val msg: String,
    val time: HourMinute? = null
)
