package com.asivers.mycalendar.composable.month

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.HolidaysInfo
import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.data.scheme.size.SizeScheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getSizeScheme

@Preview(showBackground = true)
@Composable
fun MonthViewPreview() {
    MonthView(
        selectedYear = remember { mutableIntStateOf(getCurrentYear()) },
        selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
        showYearView = remember { mutableStateOf(false) },
        lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) },
        holidaysInfo = DEFAULT_HOLIDAYS_INFO,
        colorScheme = SUMMER,
        sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun MonthView(
    modifier: Modifier = Modifier,
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    showYearView: MutableState<Boolean>,
    lastSelectedYearFromMonthView: MutableIntState,
    holidaysInfo: HolidaysInfo,
    colorScheme: ColorScheme,
    sizeScheme: SizeScheme
) {
    Column(modifier = modifier) {
        var horizontalOffset by remember { mutableFloatStateOf(0f) }
        Column(
            modifier = Modifier
                .weight(1f)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                            horizontalOffset = 0f
                        },
                        onDragEnd = {
                            if (horizontalOffset > 50f) {
                                previousMonth(selectedYear, selectedMonthIndex,
                                    lastSelectedYearFromMonthView)
                            } else if (horizontalOffset < -50f) {
                                nextMonth(selectedYear, selectedMonthIndex,
                                    lastSelectedYearFromMonthView)
                            }
                        }
                    ) { _, dragAmount ->
                        horizontalOffset += dragAmount
                    }
                }
        ) {
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                TopDropdownsRow(
                    selectedYear = selectedYear,
                    selectedMonthIndex = selectedMonthIndex,
                    showYearView = showYearView.value,
                    lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                    colorScheme = colorScheme,
                    sizeScheme = sizeScheme
                )
            }
            Box(modifier = Modifier.weight(7f)) {
                MonthCalendarGrid(
                    monthInfo = getMonthInfo(
                        selectedYear.intValue, selectedMonthIndex.intValue, holidaysInfo),
                    colorScheme = colorScheme,
                    sizeScheme = sizeScheme
                )
            }
        }
        YearViewButton(
            showYearView = showYearView,
            colorScheme = colorScheme,
            sizeScheme = sizeScheme
        )
    }
}

private fun previousMonth(
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    lastSelectedYearFromMonthView: MutableIntState
) {
    if (selectedMonthIndex.intValue == 0) {
        if (selectedYear.intValue == 1900) return
        selectedMonthIndex.intValue = 11
        selectedYear.intValue--
        lastSelectedYearFromMonthView.intValue--
    } else {
        selectedMonthIndex.intValue--
    }
}

private fun nextMonth(
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    lastSelectedYearFromMonthView: MutableIntState
) {
    if (selectedMonthIndex.intValue == 11) {
        if (selectedYear.intValue == 2100) return
        selectedMonthIndex.intValue = 0
        selectedYear.intValue++
        lastSelectedYearFromMonthView.intValue++
    } else {
        selectedMonthIndex.intValue++
    }
}