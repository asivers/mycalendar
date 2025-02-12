package com.asivers.mycalendar.composable.month

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getOnMonthSelected
import com.asivers.mycalendar.utils.getOnYearSelected
import com.asivers.mycalendar.utils.nextMonth
import com.asivers.mycalendar.utils.onHorizontalSwipe
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
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val indentFromHeaderDp = getIndentFromHeaderDp(LocalConfiguration.current.screenHeightDp)
    Column(
        modifier = modifier.padding(0.dp, indentFromHeaderDp.dp, 0.dp, 0.dp)
    ) {
        with(sharedTransitionScope) {
            TopDropdownsRow(
                modifier = Modifier
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
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.weight(17f)) {
            Column(modifier = Modifier.weight(1f)) {
                MonthCalendarGrid(
                    selectedDateState = selectedDateState,
                    onDaySelected = onDaySelected,
                    weekendMode = weekendMode,
                    schemes = schemes
                )
                val toNextMonth = {
                    selectedDateState.value = nextMonth(
                        selectedDateInfo = selectedDateState.value,
                        byMonthSwipe = true
                    )
                }
                val toPreviousMonth = {
                    selectedDateState.value = previousMonth(
                        selectedDateInfo = selectedDateState.value,
                        byMonthSwipe = true
                    )
                }
                ClickableSpacers(
                    modifier = Modifier
                        .fillMaxSize()
                        .onHorizontalSwipe(
                            horizontalOffset = remember { mutableFloatStateOf(0f) },
                            onSwipeToLeft = toNextMonth,
                            onSwipeToRight = toPreviousMonth
                        ),
                    onClickLeft = toPreviousMonth,
                    onClickRight = toNextMonth
                )
            }
            YearViewButton(
                onClick = { onYearViewButtonClick() },
                schemes = schemes
            )
        }
    }
}
