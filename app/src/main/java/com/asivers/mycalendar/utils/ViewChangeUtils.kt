package com.asivers.mycalendar.utils

import androidx.compose.runtime.MutableIntState
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

fun getOnDaySelectedCallback(
    selectedDay: MutableIntState,
    viewShownInfo: MutableState<ViewShownInfo>
): (Int) -> Unit {
    return { dayValue: Int ->
        selectedDay.intValue = dayValue
        viewShownInfo.value = ViewShownInfo(
            current = ViewShown.DAY,
            previous = viewShownInfo.value.current,
            yearViewWasShown = viewShownInfo.value.yearViewWasShown
        )
    }
}
