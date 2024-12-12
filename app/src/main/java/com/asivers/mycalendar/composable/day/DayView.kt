package com.asivers.mycalendar.composable.day

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.composable.month.ClickableSpacers
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.SwipeType
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.changeDay
import com.asivers.mycalendar.utils.fadeNormal
import com.asivers.mycalendar.utils.getDifferenceInDays
import com.asivers.mycalendar.utils.getHolidayInfo
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getOnMonthSelected
import com.asivers.mycalendar.utils.getOnYearSelected
import com.asivers.mycalendar.utils.nextDay
import com.asivers.mycalendar.utils.noTransform
import com.asivers.mycalendar.utils.previousDay
import com.asivers.mycalendar.utils.slideWeek
import com.asivers.mycalendar.utils.translateHolidayInfo

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DayView(
    modifier: Modifier = Modifier,
    selectedDateState: MutableState<SelectedDateInfo>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    thisMonthInfo: MonthInfo,
    locale: ExistingLocale,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val indentFromHeaderDp = getIndentFromHeaderDp(LocalConfiguration.current.screenHeightDp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, indentFromHeaderDp.dp, 0.dp, 0.dp)
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(0.dp, 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = selectedDateState.value,
                transitionSpec = {
                    when (val differenceInDays = getDifferenceInDays(targetState, initialState)) {
                        0 -> noTransform()
                        in -3..3 -> slideWeek(differenceInDays)
                        else -> fadeNormal()
                    }
                },
                label = "animated content days line"
            ) { selectedDateInfo ->
                DaysLine(
                    onDayChanged = { thisDayValue, inMonth ->
                        selectedDateState.value = changeDay(selectedDateInfo, thisDayValue, inMonth)
                    },
                    onSwipe = {
                        selectedDateState.value = if (it == SwipeType.RIGHT)
                            nextDay(selectedDateInfo) else previousDay(selectedDateInfo)
                    },
                    selectedDay = selectedDateInfo.dayOfMonth,
                    thisMonthInfo = thisMonthInfo,
                    weekendMode = weekendMode,
                    schemes = schemes
                )
            }
            val holidayInfo = getHolidayInfo(
                selectedDateState.value.dayOfMonth,
                thisMonthInfo.holidaysAndNotHolidays
            )
            Text(
                text = translateHolidayInfo(holidayInfo, locale),
                modifier = Modifier.padding(16.dp, 20.dp),
                fontFamily = MONTSERRAT_MEDIUM,
                fontSize = schemes.size.font.dropdownItem,
                color = Color.White
            )
            ClickableSpacers(
                modifier = Modifier.weight(1f),
                onClickLeft = { selectedDateState.value = previousDay(selectedDateState.value) },
                onClickRight = { selectedDateState.value = nextDay(selectedDateState.value) }
            )
        }
    }
}
