package com.asivers.mycalendar.composable.day

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_RIPPLE_INTERACTION_SOURCE
import com.asivers.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.PreviewFrameWithSettingsHeader
import com.asivers.mycalendar.utils.fadeSlow
import com.asivers.mycalendar.utils.getCurrentDayOfMonth
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayInMonthGridInfo
import com.asivers.mycalendar.utils.getDayViewBackgroundGradient
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.nextMonth
import com.asivers.mycalendar.utils.previousMonth

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
                    weekendMode = WeekendMode.SATURDAY_SUNDAY,
                    schemes = schemes
                )
            }
        }
    }
}

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
            targetState = selectedDay.intValue,
            transitionSpec = { fadeSlow() },
            label = "day view animated content by day"
        ) { selectedDayValue ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(0.dp, 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(0.dp, 3.dp)
                ) {
                    repeat(7) {
                        val dayValueRaw = selectedDayValue + it - 3
                        val dayInMonthGridInfo = getDayInMonthGridInfo(dayValueRaw, thisMonthInfo, weekendMode)
                        val dayValue = dayInMonthGridInfo.dayValue
                        val inThisMonth = dayInMonthGridInfo.inThisMonth
                        val isToday = dayInMonthGridInfo.isToday
                        val isWeekend = dayInMonthGridInfo.isWeekend
                        val isHoliday = dayInMonthGridInfo.isHoliday
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .alpha(if (it == 3) 1f else 0.5f)
                                .drawBehind {
                                    if (isToday) drawCircle(
                                        color = Color.White,
                                        radius = size.minDimension / 2.1f,
                                        style = Stroke(width = 4f)
                                    )
                                }
                            ,
                            onClick = {
                                selectedDay.intValue = dayValue
                                if (!inThisMonth) {
                                    if (dayValueRaw <= 0) {
                                        previousMonth(selectedYearInfo, selectedMonthInfo)
                                    } else {
                                        nextMonth(selectedYearInfo, selectedMonthInfo)
                                    }
                                }
                            },
                            shape = RectangleShape,
                            colors = TRANSPARENT_BUTTON_COLORS,
                            contentPadding = PaddingValues(0.dp),
                            interactionSource = NO_RIPPLE_INTERACTION_SOURCE
                        ) {
                            Text(
                                text = dayValue.toString(),
                                fontFamily = MONTSERRAT_BOLD,
                                fontSize = if (it == 3) schemes.size.font.main else schemes.size.font.dropdownItem,
                                color = if (isWeekend || isHoliday) schemes.color.brightElement else Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
