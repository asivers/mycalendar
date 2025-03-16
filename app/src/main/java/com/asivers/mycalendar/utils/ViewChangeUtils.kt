package com.asivers.mycalendar.utils

import androidx.compose.runtime.MutableState
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.utils.date.changeDay

fun changeView(viewShownState: MutableState<ViewShownInfo>, changeTo: ViewShown) {
    viewShownState.value = ViewShownInfo(
        current = changeTo,
        previous = viewShownState.value.current,
        yearViewWasShown = changeTo == ViewShown.YEAR || viewShownState.value.yearViewWasShown
    )
}

fun backToPreviousView(viewShownState: MutableState<ViewShownInfo>) {
    val previousView = viewShownState.value.previous
    if (previousView != null) {
        changeView(viewShownState, previousView)
    }
}

fun getOnDaySelectedCallback(
    viewShownState: MutableState<ViewShownInfo>,
    selectedDateState: MutableState<SelectedDateInfo>
): (Int, DisplayedMonth) -> Unit {
    return { dayValue: Int, inMonth: DisplayedMonth ->
        selectedDateState.value = changeDay(selectedDateState.value, dayValue, inMonth)
        viewShownState.value = ViewShownInfo(
            current = ViewShown.DAY,
            previous = viewShownState.value.current,
            yearViewWasShown = viewShownState.value.yearViewWasShown
        )
    }
}
