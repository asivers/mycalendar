package com.asivers.mycalendar.composable.month

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_RIPPLE_INTERACTION_SOURCE
import com.asivers.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.fadeSlow
import com.asivers.mycalendar.utils.getDayInfo
import com.asivers.mycalendar.utils.nextMonth
import com.asivers.mycalendar.utils.noTransform
import com.asivers.mycalendar.utils.onHorizontalSwipe
import com.asivers.mycalendar.utils.previousMonth
import com.asivers.mycalendar.utils.slideFromLeftToRight
import com.asivers.mycalendar.utils.slideFromRightToLeft

@Composable
fun MonthCalendarGrid(
    modifier: Modifier = Modifier,
    selectedDateState: MutableState<SelectedDateInfo>,
    onDaySelected: (Int, DisplayedMonth) -> Unit,
    monthInfo: MonthInfo,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val updateSelectedDateState: (SelectedDateInfo) -> Unit = { selectedDateState.value = it }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp, 0.dp)
    ) {
        HeaderWeekInMonthCalendarGrid(
            schemes = schemes
        )
        AnimatedContent(
            targetState = selectedDateState.value,
            transitionSpec = {
                if (targetState.byDropdown) {
                    fadeSlow()
                } else {
                    val monthIndexDiff = targetState.monthIndex - initialState.monthIndex
                    when (monthIndexDiff) {
                        0 -> noTransform() // workaround to prevent slide before day view
                        1, -11 -> slideFromRightToLeft() // next month
                        else -> slideFromLeftToRight() // prev month
                    }
                }
            },
            label = "month calendar animated content"
        ) {
            SixWeeksInMonthCalendarGrid(
                onDaySelected = onDaySelected,
                onSwipeToLeft = { updateSelectedDateState(nextMonth(it)) },
                onSwipeToRight = { updateSelectedDateState(previousMonth(it)) },
                monthInfo = monthInfo,
                weekendMode = weekendMode,
                schemes = schemes
            )
        }
    }
}

@Composable
fun HeaderWeekInMonthCalendarGrid(
    modifier: Modifier = Modifier,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(7) { dayOfWeekIndex ->
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 5.dp),
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
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(0.dp, 3.dp)
    ) {
        repeat(7) { dayOfWeekIndex ->
            DayInMonthCalendarGrid(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                onDaySelected = onDaySelected,
                weekIndex = weekIndex,
                dayOfWeekIndex = dayOfWeekIndex,
                monthInfo = monthInfo,
                weekendMode = weekendMode,
                schemes = schemes
            )
        }
    }
}

@Composable
fun DayInMonthCalendarGrid(
    modifier: Modifier = Modifier,
    onDaySelected: (Int, DisplayedMonth) -> Unit,
    weekIndex: Int,
    dayOfWeekIndex: Int,
    monthInfo: MonthInfo,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val dayValueRaw = weekIndex * 7 + dayOfWeekIndex - monthInfo.dayOfWeekOf1st + 1
    val dayInfo = getDayInfo(dayValueRaw, monthInfo, weekendMode)
    val dayValue = dayInfo.dayValue
    val inMonth = dayInfo.inMonth
    val inThisMonth = inMonth == DisplayedMonth.THIS
    val isToday = dayInfo.isToday
    val isWeekend = dayInfo.isWeekend
    val isHoliday = dayInfo.isHoliday
    val isWithNote = dayInfo.isWithNote
    Button(
        modifier = modifier
            .alpha(if (inThisMonth) 1f else 0.25f)
            .drawBehind {
                if (isToday) drawCircle(
                    color = schemes.color.text,
                    radius = size.minDimension / 2.1f,
                    style = Stroke(width = 4f)
                )
            },
        onClick = { onDaySelected(dayValue, inMonth) },
        shape = RectangleShape,
        colors = TRANSPARENT_BUTTON_COLORS,
        contentPadding = PaddingValues(0.dp),
        interactionSource = NO_RIPPLE_INTERACTION_SOURCE
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dayValue.toString(),
                fontFamily = MONTSERRAT_BOLD,
                fontSize = if (inThisMonth) schemes.size.font.main else schemes.size.font.dropdownItem,
                color = if (isWeekend || isHoliday) schemes.color.brightElement else schemes.color.text,
                textAlign = TextAlign.Center
            )
            if (isWithNote) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(schemes.color.text, CircleShape)
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
