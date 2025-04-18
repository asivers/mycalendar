package com.asivers.mycalendar.composable.month

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.WeekNumbersMode
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.date.getDayInfo
import com.asivers.mycalendar.utils.date.getMonthInfoForMonthView
import com.asivers.mycalendar.utils.date.nextMonth
import com.asivers.mycalendar.utils.date.previousMonth
import com.asivers.mycalendar.utils.fadeSlow
import com.asivers.mycalendar.utils.noTransform
import com.asivers.mycalendar.utils.onHorizontalSwipe
import com.asivers.mycalendar.utils.slideFromLeftToRight
import com.asivers.mycalendar.utils.slideFromRightToLeft

@Composable
fun MonthCalendarGrid(
    modifier: Modifier = Modifier,
    selectedDateState: MutableState<SelectedDateInfo>,
    onDaySelected: (Int, DisplayedMonth) -> Unit,
    weekendMode: WeekendMode,
    weekNumbersMode: WeekNumbersMode,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val updateSelectedDateState: (SelectedDateInfo) -> Unit = { selectedDateState.value = it }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp, 0.dp)
    ) {
        HeaderWeekInMonthCalendarGrid(
            weekNumbersMode = weekNumbersMode,
            schemes = schemes
        )
        Spacer(modifier = Modifier.height(5.dp))
        AnimatedContent(
            targetState = selectedDateState.value,
            transitionSpec = {
                if (targetState.byMonthSwipe) {
                    val monthValueDiff = targetState.monthValue - initialState.monthValue
                    when (monthValueDiff) {
                        1, -11 -> slideFromRightToLeft() // next month
                        -1, 11 -> slideFromLeftToRight() // prev month
                        else -> noTransform() // will never happen
                    }
                } else {
                    fadeSlow()
                }
            },
            label = "month calendar animated content"
        ) { selectedDateInfo ->
            val countryHolidayScheme = schemes.countryHoliday
            val monthInfo = remember(selectedDateInfo, countryHolidayScheme) {
                getMonthInfoForMonthView(
                    ctx = ctx,
                    year = selectedDateInfo.year,
                    monthValue = selectedDateInfo.monthValue,
                    countryHolidayScheme = countryHolidayScheme,
                    weekNumbersMode = weekNumbersMode
                )
            }
            SixWeeksInMonthCalendarGrid(
                onDaySelected = onDaySelected,
                onSwipeToLeft = {
                    updateSelectedDateState(nextMonth(selectedDateInfo, true))
                },
                onSwipeToRight = {
                    updateSelectedDateState(previousMonth(selectedDateInfo, true))
                },
                monthInfo = monthInfo,
                weekendMode = weekendMode,
                weekNumbersMode = weekNumbersMode,
                schemes = schemes
            )
        }
    }
}

@Composable
fun HeaderWeekInMonthCalendarGrid(
    modifier: Modifier = Modifier,
    weekNumbersMode: WeekNumbersMode,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        if (weekNumbersMode == WeekNumbersMode.ON) {
            Spacer(modifier.weight(4f))
        }
        repeat(7) { dayOfWeekIndex ->
            Text(
                modifier = Modifier.weight(6f),
                text = schemes.translation.daysOfWeek3[dayOfWeekIndex],
                fontFamily = MONTSERRAT,
                fontSize = schemes.size.font.mvHeaderWeek,
                color = schemes.color.text,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SixWeeksInMonthCalendarGrid(
    modifier: Modifier = Modifier,
    onDaySelected: (Int, DisplayedMonth) -> Unit,
    onSwipeToLeft: () -> Unit,
    onSwipeToRight: () -> Unit,
    monthInfo: MonthInfo,
    weekendMode: WeekendMode,
    weekNumbersMode: WeekNumbersMode,
    schemes: SchemeContainer
) {
    Column(
        modifier = modifier.onHorizontalSwipe(
            horizontalOffset = remember { mutableFloatStateOf(0f) },
            onSwipeToLeft = onSwipeToLeft,
            onSwipeToRight = onSwipeToRight
        )
    ) {
        repeat(6) { weekIndex ->
            WeekInMonthCalendarGrid(
                onDaySelected = onDaySelected,
                weekIndex = weekIndex,
                monthInfo = monthInfo,
                weekendMode = weekendMode,
                weekNumbersMode = weekNumbersMode,
                schemes = schemes
            )
        }
    }
}

@Composable
fun WeekInMonthCalendarGrid(
    modifier: Modifier = Modifier,
    onDaySelected: (Int, DisplayedMonth) -> Unit,
    weekIndex: Int,
    monthInfo: MonthInfo,
    weekendMode: WeekendMode,
    weekNumbersMode: WeekNumbersMode,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(0.dp, 3.dp)
    ) {
        if (weekNumbersMode == WeekNumbersMode.ON) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = monthInfo.weekNumbers[weekIndex].toString(),
                    fontFamily = MONTSERRAT,
                    fontSize = schemes.size.font.mvHeaderWeek,
                    color = schemes.color.text
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        repeat(7) { dayOfWeekIndex ->
            val dayValueRaw = weekIndex * 7 + dayOfWeekIndex - monthInfo.dayOfWeekOf1st + 2
            val dayInfo = getDayInfo(dayValueRaw, monthInfo, weekendMode)
            DayWithNoteMark(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(6f),
                onDaySelected = onDaySelected,
                dayInfo = dayInfo,
                schemes = schemes
            )
        }
    }
}
