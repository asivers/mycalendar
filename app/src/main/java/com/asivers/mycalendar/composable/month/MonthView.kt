package com.asivers.mycalendar.composable.month

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getOnMonthSelected
import com.asivers.mycalendar.utils.getOnYearSelected
import com.asivers.mycalendar.utils.nextMonth
import com.asivers.mycalendar.utils.previousMonth

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MonthView(
    modifier: Modifier = Modifier,
    selectedDateState: MutableState<SelectedDateInfo>,
    onYearViewButtonClick: () -> Unit,
    onDaySelected: (Int, DisplayedMonth) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    monthInfo: MonthInfo,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val indentFromHeaderDp = getIndentFromHeaderDp(LocalConfiguration.current.screenHeightDp)
    var horizontalOffset by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier
            .padding(0.dp, indentFromHeaderDp.dp, 0.dp, 0.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        horizontalOffset = 0f
                    },
                    onDragEnd = {
                        if (horizontalOffset > 50f) {
                            selectedDateState.value = previousMonth(selectedDateState.value)
                        } else if (horizontalOffset < -50f) {
                            selectedDateState.value = nextMonth(selectedDateState.value)
                        }
                    }
                ) { _, dragAmount ->
                    horizontalOffset += dragAmount
                }
            }
    ) {
        with(sharedTransitionScope) {
            TopDropdownsRow(
                modifier = Modifier
                    .weight(1f)
                    .sharedElement(
                        rememberSharedContentState(key = "topDropdownsRow"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                onYearSelected = { getOnYearSelected(selectedDateState, it, false) },
                onMonthSelected = { getOnMonthSelected(selectedDateState, it) },
                selectedDateInfo = selectedDateState.value,
                forYearView = false,
                schemes = schemes
            )
        }
        Column(modifier = Modifier.weight(8f)) {
            MonthCalendarGrid(
                selectedDateState = selectedDateState,
                onDaySelected = onDaySelected,
                monthInfo = monthInfo,
                weekendMode = weekendMode,
                schemes = schemes
            )
            ClickableSpacers(
                modifier = Modifier.fillMaxSize(),
                onClickLeft = { selectedDateState.value = previousMonth(selectedDateState.value) },
                onClickRight = { selectedDateState.value = nextMonth(selectedDateState.value) }
            )
        }
        YearViewButton(
            onClick = { onYearViewButtonClick() },
            schemes = schemes
        )
    }
}
