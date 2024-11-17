package com.asivers.mycalendar.views.year

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.constants.YEAR_VIEW_BACKGROUND_GRADIENT
import com.asivers.mycalendar.data.HolidaysInfo
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.views.month.TopDropdownsRow

@Preview(showBackground = true)
@Composable
fun YearViewContentPreview() {
    YearViewContent(
        modifier = Modifier,
        selectedYear = remember { mutableIntStateOf(getCurrentYear()) },
        selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
        showYearView = remember { mutableStateOf(true) },
        yearFromMonthView = getCurrentYear(),
        holidaysInfo = DEFAULT_HOLIDAYS_INFO
    )
}

@Composable
fun YearViewContent(
    modifier: Modifier,
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    showYearView: MutableState<Boolean>,
    yearFromMonthView: Int,
    holidaysInfo: HolidaysInfo
) {
    var horizontalOffset by remember { mutableFloatStateOf(0f) }
    var verticalOffset by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier
            .background(YEAR_VIEW_BACKGROUND_GRADIENT)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        horizontalOffset = 0f
                        verticalOffset = 0f
                    },
                    onDragEnd = {
                        if (verticalOffset > 100f) {
                            selectedYear.intValue = yearFromMonthView
                            showYearView.value = false
                        } else if (horizontalOffset > 100f) {
                            selectedYear.intValue--
                        } else if (horizontalOffset < -100f) {
                            selectedYear.intValue++
                        }
                    }
                ) { _, dragAmount ->
                    horizontalOffset += dragAmount.x
                    verticalOffset += dragAmount.y
                }
            }
    ) {
        TopDropdownsRow(
            selectedYear = selectedYear,
            selectedMonthIndex = selectedMonthIndex,
            showYearView = showYearView.value
        )
        Box(
            modifier = Modifier.weight(1f)
        ) {
            YearCalendarGrid(
                year = selectedYear.intValue,
                selectedMonthIndex = selectedMonthIndex,
                showYearView = showYearView,
                holidaysInfo = holidaysInfo
            )
        }
    }
}
