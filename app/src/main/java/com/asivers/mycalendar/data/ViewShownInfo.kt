package com.asivers.mycalendar.data

import com.asivers.mycalendar.enums.ViewShown

data class ViewShownInfo(
    val current: ViewShown,
    val previous: ViewShown? = null,
    val yearViewWasShown: Boolean = false // workaround to speed up the first year view appearance
)
