package com.asivers.mycalendar.composable.day

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.addDays
import com.asivers.mycalendar.utils.changeDay
import com.asivers.mycalendar.utils.fadeNormal
import com.asivers.mycalendar.utils.getDifferenceInDays
import com.asivers.mycalendar.utils.getHolidayInfo
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getOnMonthSelected
import com.asivers.mycalendar.utils.getOnYearSelected
import com.asivers.mycalendar.utils.noTransform
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
                .padding(0.dp, 20.dp, 0.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = selectedDateState.value,
                transitionSpec = {
                    when (val differenceInDays = getDifferenceInDays(targetState, initialState)) {
                        0 -> noTransform()
                        in -7..7 -> slideWeek(differenceInDays)
                        else -> fadeNormal()
                    }
                },
                label = "animated content days line"
            ) { selectedDateInfo ->
                DaysLine(
                    onDayChanged = { thisDayValue, inMonth ->
                        selectedDateState.value = changeDay(selectedDateInfo, thisDayValue, inMonth)
                    },
                    onSwipe = { selectedDateState.value = addDays(selectedDateInfo, it) },
                    selectedDay = selectedDateInfo.dayOfMonth,
                    thisMonthInfo = thisMonthInfo,
                    weekendMode = weekendMode,
                    schemes = schemes
                )
            }
            val selectedDateInfo = selectedDateState.value
            val holidaysAndNotHolidays = thisMonthInfo.holidaysAndNotHolidays
            val holidayInfo = getHolidayInfo(selectedDateInfo.dayOfMonth, holidaysAndNotHolidays)
                ?.translateHolidayInfo(locale)
            NotesSection(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                selectedDateInfo = selectedDateInfo,
                holidayInfo = holidayInfo,
                schemes = schemes
            )
        }
    }
}
