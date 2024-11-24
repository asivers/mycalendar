package com.asivers.mycalendar.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DayType {

    @SerialName("holiday")
    HOLIDAY,

    @SerialName("not_holiday")
    NOT_HOLIDAY,

    @SerialName("note")
    NOTE

}
