package com.asivers.mycalendar.composable.year

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.changeView
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getOnMonthSelected
import com.asivers.mycalendar.utils.getOnYearSelected
import com.asivers.mycalendar.utils.getYearViewBackgroundGradient

@Composable
fun YearView(
    modifier: Modifier = Modifier,
    viewShownState: MutableState<ViewShownInfo>,
    selectedDateState: MutableState<SelectedDateInfo>,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val indentFromHeaderToDropdownsDp = getIndentFromHeaderDp(LocalConfiguration.current.screenHeightDp)
    val indentFromHeaderToFrameDp = 12
    val indentFromFrameToDropdowns = indentFromHeaderToDropdownsDp - indentFromHeaderToFrameDp
    var horizontalOffset by remember { mutableFloatStateOf(0f) }
    var verticalOffset by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier
            .padding(0.dp, indentFromHeaderToFrameDp.dp, 0.dp, 0.dp)
            .clip(RoundedCornerShape(36.dp, 36.dp))
            .background(getYearViewBackgroundGradient(schemes.color))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        horizontalOffset = 0f
                        verticalOffset = 0f
                    },
                    onDragEnd = {
                        val yearBeforeUpdate = selectedDateState.value.year
                        val lastSelectedYearFromMonthView = selectedDateState.value.yearOnMonthView
                        val monthIndex = selectedDateState.value.monthIndex
                        if (verticalOffset > 100f) {
                            selectedDateState.value = SelectedDateInfo(
                                year = lastSelectedYearFromMonthView,
                                monthIndex = monthIndex
                            )
                            changeView(viewShownState, ViewShown.MONTH)
                        } else if (horizontalOffset > 50f && yearBeforeUpdate > 1900) {
                            selectedDateState.value = SelectedDateInfo(
                                year = yearBeforeUpdate - 1,
                                monthIndex = monthIndex,
                                yearOnMonthView = lastSelectedYearFromMonthView
                            )
                        } else if (horizontalOffset < -50f && yearBeforeUpdate < 2100) {
                            selectedDateState.value = SelectedDateInfo(
                                year = yearBeforeUpdate + 1,
                                monthIndex = monthIndex,
                                yearOnMonthView = lastSelectedYearFromMonthView
                            )
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
            onYearSelected = { getOnYearSelected(selectedDateState, it, true) },
            onMonthSelected = { getOnMonthSelected(selectedDateState, it) },
            selectedDateInfo = selectedDateState.value,
            forYearView = true,
            schemes = schemes
        )
        Box(
            modifier = Modifier.weight(1f)
        ) {
            YearCalendarGrid(
                viewShownState = viewShownState,
                selectedDateState = selectedDateState,
                weekendMode = weekendMode,
                schemes = schemes
            )
        }
    }
}
