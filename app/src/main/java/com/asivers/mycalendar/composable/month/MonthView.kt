package com.asivers.mycalendar.composable.month

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.composable.settings.SettingsHeader
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.noRippleClickable

@Preview(showBackground = true)
@Composable
fun MonthViewPreview() {
    MonthView(
        selectedYear = remember { mutableIntStateOf(getCurrentYear()) },
        selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
        viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.MONTH)) },
        lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) },
        weekendMode = WeekendMode.SATURDAY_SUNDAY,
        schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun MonthView(
    modifier: Modifier = Modifier,
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    viewShownInfo: MutableState<ViewShownInfo>,
    lastSelectedYearFromMonthView: MutableIntState,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val indentFromHeaderDp = getIndentFromHeaderDp(LocalConfiguration.current.screenHeightDp)
    Column(modifier = modifier) {
        SettingsHeader(
            viewShownInfo = viewShownInfo,
            schemes = schemes
        )
        Spacer(modifier = Modifier.height(indentFromHeaderDp.dp))
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
                                previousMonth(
                                    selectedYear,
                                    selectedMonthIndex,
                                    lastSelectedYearFromMonthView
                                )
                            } else if (horizontalOffset < -50f) {
                                nextMonth(
                                    selectedYear,
                                    selectedMonthIndex,
                                    lastSelectedYearFromMonthView
                                )
                            }
                        }
                    ) { _, dragAmount ->
                        horizontalOffset += dragAmount
                    }
                }
        ) {
            TopDropdownsRow(
                modifier = Modifier.weight(1f),
                selectedYear = selectedYear,
                selectedMonthIndex = selectedMonthIndex,
                showYearView = viewShownInfo.value.current == ViewShown.YEAR,
                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                schemes = schemes
            )
            Column(modifier = Modifier.weight(8f)) {
                val monthInfo = getMonthInfo(
                    year = selectedYear.intValue,
                    monthIndex = selectedMonthIndex.intValue,
                    countryHolidayScheme = schemes.countryHoliday
                )
                MonthCalendarGrid(
                    monthInfo = monthInfo,
                    weekendMode = weekendMode,
                    schemes = schemes
                )
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(3f)
                            .noRippleClickable {
                                previousMonth(
                                    selectedYear,
                                    selectedMonthIndex,
                                    lastSelectedYearFromMonthView
                                )
                            }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(3f)
                            .noRippleClickable {
                                nextMonth(
                                    selectedYear,
                                    selectedMonthIndex,
                                    lastSelectedYearFromMonthView
                                )
                            }
                    )
                }
            }
        }
        YearViewButton(
            viewShownInfo = viewShownInfo,
            schemes = schemes
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
