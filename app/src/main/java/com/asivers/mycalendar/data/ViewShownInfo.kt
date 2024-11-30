package com.asivers.mycalendar.data

import com.asivers.mycalendar.enums.ViewShown

data class ViewShownInfo(
    val current: ViewShown,
    val previous: ViewShown? = null
)
