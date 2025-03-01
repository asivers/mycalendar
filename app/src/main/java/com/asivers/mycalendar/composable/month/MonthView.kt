package com.asivers.mycalendar.composable.month

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.WeekNumbersMode
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.date.changeMonth
import com.asivers.mycalendar.utils.date.changeYear
import com.asivers.mycalendar.utils.date.nextMonth
import com.asivers.mycalendar.utils.date.previousMonth
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getYearViewButtonGradient

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
    weekNumbersMode: WeekNumbersMode,
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
                onYearSelected = { selectedDateState.value = changeYear(selectedDateState.value, it) },
                onMonthSelected = { selectedDateState.value = changeMonth(selectedDateState.value, it) },
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
                    weekNumbersMode = weekNumbersMode,
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
                    onClickLeft = toPreviousMonth,
                    onClickRight = toNextMonth,
                    swipeEnabled = true
                )
            }
            BottomViewButton(
                onClick = { onYearViewButtonClick() },
                text = schemes.translation.yearView,
                background = getYearViewButtonGradient(schemes.color),
                schemes = schemes
            )
        }
    }
}
