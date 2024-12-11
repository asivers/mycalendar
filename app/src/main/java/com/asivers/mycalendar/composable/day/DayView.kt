package com.asivers.mycalendar.composable.day

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.composable.month.ClickableSpacers
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.PreviewFrameWithSettingsHeader
import com.asivers.mycalendar.utils.changeDay
import com.asivers.mycalendar.utils.fadeSlow
import com.asivers.mycalendar.utils.getCurrentDayOfMonth
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayViewBackgroundGradient
import com.asivers.mycalendar.utils.getHolidayInfo
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.nextDay
import com.asivers.mycalendar.utils.previousDay
import com.asivers.mycalendar.utils.translateHolidayInfo

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun DayViewPreview() {
    val year = getCurrentYear()
    val monthIndex = getCurrentMonthIndex()
    val schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    val viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.DAY, ViewShown.MONTH)) }
    SharedTransitionLayout {
        AnimatedContent(
            targetState = viewShownInfo.value,
            label = "preview day view animated content"
        ) { viewShownInfoValue ->
            PreviewFrameWithSettingsHeader(
                viewShown = viewShownInfoValue.current,
                getBackground = { getDayViewBackgroundGradient(it) },
                schemes = schemes
            ) {
                DayView(
                    selectedYearInfo = remember { mutableStateOf(SelectedYearInfo(year)) },
                    selectedMonthInfo = remember { mutableStateOf(SelectedMonthInfo(year, monthIndex)) },
                    selectedDay = remember { mutableIntStateOf(getCurrentDayOfMonth()) },
                    viewShownInfo = viewShownInfo,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedContent,
                    thisMonthInfo = getMonthInfo(year, monthIndex, schemes.countryHoliday, false),
                    locale = ExistingLocale.EN,
                    weekendMode = WeekendMode.SATURDAY_SUNDAY,
                    schemes = schemes
                )
            }
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DayView(
    modifier: Modifier = Modifier,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    selectedDay: MutableIntState,
    viewShownInfo: MutableState<ViewShownInfo>,
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
                selectedYearInfo = selectedYearInfo,
                selectedMonthInfo = selectedMonthInfo,
                viewShownInfo = viewShownInfo,
                schemes = schemes
            )
        }
        AnimatedContent(
            targetState = selectedMonthInfo.value,
            transitionSpec = { fadeSlow() },
            label = "day view animated content by dropdowns"
        ) {
            AnimatedContent(
                targetState = selectedDay.intValue,
                transitionSpec = { fadeSlow() },
                label = "day view animated content by day selection"
            ) { selectedDayValue ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(0.dp, 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DaysLine(
                        onDayChanged = { thisDayValue, inThisMonth ->
                            changeDay(selectedYearInfo, selectedMonthInfo, selectedDay, thisDayValue, inThisMonth)
                        },
                        selectedDay = selectedDayValue,
                        thisMonthInfo = thisMonthInfo,
                        weekendMode = weekendMode,
                        schemes = schemes
                    )
                    val holidayInfo = getHolidayInfo(selectedDayValue, thisMonthInfo.holidaysAndNotHolidays)
                    Text(
                        text = translateHolidayInfo(holidayInfo, locale),
                        modifier = Modifier.padding(16.dp, 20.dp),
                        fontFamily = MONTSERRAT_MEDIUM,
                        fontSize = schemes.size.font.dropdownItem,
                        color = Color.White
                    )
                    ClickableSpacers(
                        modifier = Modifier.weight(1f),
                        onClickLeft = { previousDay(selectedYearInfo, selectedMonthInfo, selectedDay) },
                        onClickRight = { nextDay(selectedYearInfo, selectedMonthInfo, selectedDay) }
                    )
                }
            }
        }
    }
}
