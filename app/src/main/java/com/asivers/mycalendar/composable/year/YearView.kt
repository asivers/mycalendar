package com.asivers.mycalendar.composable.year

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
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
import com.asivers.mycalendar.utils.changeView
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.getYearViewBackgroundGradient

@Preview(showBackground = true)
@Composable
fun YearViewPreview() {
    YearView(
        selectedYear = remember { mutableIntStateOf(getCurrentYear()) },
        selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
        viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.YEAR, ViewShown.MONTH)) },
        lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) },
        weekendMode = WeekendMode.SATURDAY_SUNDAY,
        schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun YearView(
    modifier: Modifier = Modifier,
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    viewShownInfo: MutableState<ViewShownInfo>,
    lastSelectedYearFromMonthView: MutableIntState,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
//    val paddingTopDp = ((screenHeightDp - 48 - 32) * 3 / (3 + 13 + 80)) - 8
    val paddingTopDp = (screenHeightDp - 80) / 32 - 8
    Column(modifier = modifier) {
        SettingsHeader(
            viewShownInfo = viewShownInfo,
            schemes = schemes
        )
        var horizontalOffset by remember { mutableFloatStateOf(0f) }
        var verticalOffset by remember { mutableFloatStateOf(0f) }
        Column(
            modifier = modifier
                .padding(0.dp, paddingTopDp.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(36.dp, 36.dp))
                .background(getYearViewBackgroundGradient(schemes.color))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            horizontalOffset = 0f
                            verticalOffset = 0f
                        },
                        onDragEnd = {
                            if (verticalOffset > 100f) {
                                selectedYear.intValue = lastSelectedYearFromMonthView.intValue
                                changeView(viewShownInfo, ViewShown.MONTH)
                            } else if (horizontalOffset > 50f && selectedYear.intValue > 1900) {
                                selectedYear.intValue--
                            } else if (horizontalOffset < -50f && selectedYear.intValue < 2100) {
                                selectedYear.intValue++
                            }
                        }
                    ) { _, dragAmount ->
                        horizontalOffset += dragAmount.x
                        verticalOffset += dragAmount.y
                    }
                }
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            TopDropdownsRow(
                selectedYear = selectedYear,
                selectedMonthIndex = selectedMonthIndex,
                showYearView = viewShownInfo.value.current == ViewShown.YEAR,
                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                schemes = schemes
            )
            Box(
                modifier = Modifier.weight(1f)
            ) {
                YearCalendarGrid(
                    thisYear = selectedYear.intValue,
                    selectedMonthIndex = selectedMonthIndex,
                    viewShownInfo = viewShownInfo,
                    lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                    weekendMode = weekendMode,
                    schemes = schemes
                )
            }
        }
    }
}
