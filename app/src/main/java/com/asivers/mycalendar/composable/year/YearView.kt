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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.changeView
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.getYearViewBackgroundGradient

@Preview(showBackground = true)
@Composable
fun YearViewPreview() {
    YearView(
        selectedYearInfo = remember { mutableStateOf(SelectedYearInfo(getCurrentYear())) },
        selectedMonthInfo = remember { mutableStateOf(SelectedMonthInfo(getCurrentYear(), getCurrentMonthIndex())) },
        viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.MONTH)) },
        weekendMode = WeekendMode.SATURDAY_SUNDAY,
        schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun YearView(
    modifier: Modifier = Modifier,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    viewShownInfo: MutableState<ViewShownInfo>,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val indentFromHeaderToDropdownsDp = getIndentFromHeaderDp(LocalConfiguration.current.screenHeightDp)
    val indentFromHeaderToFrameDp = 12
    val indentFromFrameToDropdowns = indentFromHeaderToDropdownsDp - indentFromHeaderToFrameDp
    Column(modifier = modifier) {
        SettingsHeader(
            viewShownInfo = viewShownInfo,
            schemes = schemes
        )
        var horizontalOffset by remember { mutableFloatStateOf(0f) }
        var verticalOffset by remember { mutableFloatStateOf(0f) }
        Column(
            modifier = modifier
                .padding(0.dp, indentFromHeaderToFrameDp.dp, 0.dp, 0.dp)
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
                            val yearBeforeUpdate = selectedYearInfo.value.year
                            val lastSelectedYearFromMonthView = selectedMonthInfo.value.year
                            if (verticalOffset > 100f) {
                                selectedYearInfo.value = SelectedYearInfo(lastSelectedYearFromMonthView)
                                changeView(viewShownInfo, ViewShown.MONTH)
                            } else if (horizontalOffset > 50f && yearBeforeUpdate > 1900) {
                                selectedYearInfo.value = SelectedYearInfo(yearBeforeUpdate - 1)
                            } else if (horizontalOffset < -50f && yearBeforeUpdate < 2100) {
                                selectedYearInfo.value = SelectedYearInfo(yearBeforeUpdate + 1)
                            }
                        }
                    ) { _, dragAmount ->
                        horizontalOffset += dragAmount.x
                        verticalOffset += dragAmount.y
                    }
                }
        ) {
            Spacer(modifier = Modifier.height(indentFromFrameToDropdowns.dp))
            TopDropdownsRow(
                selectedYearInfo = selectedYearInfo,
                selectedMonthInfo = selectedMonthInfo,
                showYearView = viewShownInfo.value.current == ViewShown.YEAR,
                schemes = schemes
            )
            Box(
                modifier = Modifier.weight(1f)
            ) {
                YearCalendarGrid(
                    selectedYearInfo = selectedYearInfo,
                    selectedMonthInfo = selectedMonthInfo,
                    viewShownInfo = viewShownInfo,
                    weekendMode = weekendMode,
                    schemes = schemes
                )
            }
        }
    }
}
