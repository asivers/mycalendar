package com.asivers.mycalendar.utils

import androidx.compose.runtime.MutableState
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown

fun changeView(viewShownInfo: MutableState<ViewShownInfo>, changeTo: ViewShown) {
    viewShownInfo.value = ViewShownInfo(
        current = changeTo,
        previous = viewShownInfo.value.current,
        yearViewWasShown = changeTo == ViewShown.YEAR || viewShownInfo.value.yearViewWasShown
    )
}

fun backToPreviousView(viewShownInfo: MutableState<ViewShownInfo>) {
    if (viewShownInfo.value.previous != null) {
        changeView(viewShownInfo, viewShownInfo.value.previous!!)
    }
}
