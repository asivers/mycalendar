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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.addDays
import com.asivers.mycalendar.utils.changeDay
import com.asivers.mycalendar.utils.fadeNormal
import com.asivers.mycalendar.utils.getDifferenceInDays
import com.asivers.mycalendar.utils.getHolidayInfoForDay
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getMonthInfo
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
    locale: ExistingLocale,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
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
                val countryHolidayScheme = schemes.countryHoliday
                val thisMonthInfo = remember(selectedDateInfo, countryHolidayScheme) {
                    getMonthInfo(
                        year = selectedDateInfo.year,
                        monthIndex = selectedDateInfo.monthIndex,
                        countryHolidayScheme = countryHolidayScheme,
                        forView = ViewShown.DAY,
                        ctx = ctx,
                        thisDayOfMonth = selectedDateInfo.dayOfMonth
                    )
                }
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
            val holidayInfo = getHolidayInfoForDay(selectedDateState.value, schemes.countryHoliday)
                ?.translateHolidayInfo(locale)
            NotesSection(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                onSwipeToLeft = { selectedDateState.value = nextDay(selectedDateState.value) },
                onSwipeToRight = { selectedDateState.value = previousDay(selectedDateState.value) },
                refreshDaysLine = { selectedDateState.value = selectedDateState.value.cloneWithRefresh() },
                selectedDateInfo = selectedDateState.value,
                holidayInfo = holidayInfo,
                schemes = schemes
            )
        }
    }
}
