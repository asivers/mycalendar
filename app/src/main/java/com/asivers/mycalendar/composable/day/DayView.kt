package com.asivers.mycalendar.composable.day

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.date.addDays
import com.asivers.mycalendar.utils.date.changeDay
import com.asivers.mycalendar.utils.date.changeMonth
import com.asivers.mycalendar.utils.date.changeYear
import com.asivers.mycalendar.utils.date.getDifferenceInDays
import com.asivers.mycalendar.utils.date.getHolidayInfoForDay
import com.asivers.mycalendar.utils.date.getMonthInfoForDayView
import com.asivers.mycalendar.utils.date.nextDay
import com.asivers.mycalendar.utils.date.previousDay
import com.asivers.mycalendar.utils.fadeFast
import com.asivers.mycalendar.utils.fadeNormal
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
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
    locale: ExistingLocale,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val density = LocalDensity.current
    val indentFromHeaderDp = getIndentFromHeaderDp(ctx, density)
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
                onYearSelected = { selectedDateState.value = changeYear(selectedDateState.value, it) },
                onMonthSelected = { selectedDateState.value = changeMonth(selectedDateState.value, it) },
                selectedDateInfo = selectedDateState.value,
                forYearView = false,
                schemes = schemes
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.weight(17f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = selectedDateState.value,
                transitionSpec = {
                    if (targetState.byDaysLineSlide) {
                        fadeFast()
                    } else {
                        val differenceInDays = getDifferenceInDays(targetState, initialState)
                        when (differenceInDays) {
                            0 -> noTransform()
                            in -3..3 -> slideWeek(differenceInDays)
                            else -> fadeNormal()
                        }
                    }
                },
                label = "animated content days line"
            ) { selectedDateInfo ->
                val countryHolidayScheme = schemes.countryHoliday
                val thisMonthInfo = remember(selectedDateInfo, countryHolidayScheme) {
                    getMonthInfoForDayView(
                        ctx = ctx,
                        year = selectedDateInfo.year,
                        monthValue = selectedDateInfo.monthValue,
                        countryHolidayScheme = countryHolidayScheme,
                        thisDayOfMonth = selectedDateInfo.dayOfMonth
                    )
                }
                DaysLine(
                    onDayChanged = { thisDayValue, inMonth ->
                        selectedDateState.value = changeDay(selectedDateInfo, thisDayValue, inMonth)
                    },
                    onSlide = {
                        selectedDateState.value = addDays(selectedDateInfo, it, true)
                    },
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
