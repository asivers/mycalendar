package com.asivers.mycalendar.composable.month

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.asivers.mycalendar.constants.MONTSERRAT
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_RIPPLE_INTERACTION_SOURCE
import com.asivers.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.fadeSlow
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayInMonthGridInfo
import com.asivers.mycalendar.utils.getMonthAndYearViewBackgroundGradient
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.slideFromLeftToRight
import com.asivers.mycalendar.utils.slideFromRightToLeft

@Preview(showBackground = true)
@Composable
fun MonthCalendarGridPreview() {
    Box(
        modifier = Modifier
            .background(brush = getMonthAndYearViewBackgroundGradient(SUMMER))
            .fillMaxWidth()
    ) {
        MonthCalendarGrid(
            selectedMonthInfo = remember { mutableStateOf(SelectedMonthInfo(getCurrentYear(), getCurrentMonthIndex())) },
            onDaySelected = {},
            weekendMode = WeekendMode.SATURDAY_SUNDAY,
            schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
        )
    }
}

@Composable
fun MonthCalendarGrid(
    modifier: Modifier = Modifier,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    onDaySelected: (Int) -> Unit,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp, 0.dp)
    ) {
        HeaderWeekInMonthCalendarGrid(
            schemes = schemes
        )
        AnimatedContent(
            targetState = selectedMonthInfo.value,
            transitionSpec = {
                if (targetState.byDropdown) {
                    fadeSlow()
                } else {
                    val monthIndexDiff = targetState.monthIndex - initialState.monthIndex
                    val isChangedToNextMonth = monthIndexDiff == 1 || monthIndexDiff == -11
                    if (isChangedToNextMonth) slideFromRightToLeft() else slideFromLeftToRight()
                }
            },
            label = "month calendar animated content"
        ) {
            val monthInfo = getMonthInfo(
                year = it.year,
                monthIndex = it.monthIndex,
                countryHolidayScheme = schemes.countryHoliday,
                forYearView = false
            )
            Column {
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
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WeekInMonthCalendarGrid(
    modifier: Modifier = Modifier,
    onDaySelected: (Int) -> Unit,
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
    onDaySelected: (Int) -> Unit,
    weekIndex: Int,
    dayOfWeekIndex: Int,
    monthInfo: MonthInfo,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val dayValueRaw = weekIndex * 7 + dayOfWeekIndex - monthInfo.dayOfWeekOf1st + 1
    val dayInMonthGridInfo = getDayInMonthGridInfo(dayValueRaw, monthInfo, weekendMode)
    val dayValue = dayInMonthGridInfo.dayValue
    val inThisMonth = dayInMonthGridInfo.inThisMonth
    val isToday = dayInMonthGridInfo.isToday
    val isWeekend = dayInMonthGridInfo.isWeekend
    val isHoliday = dayInMonthGridInfo.isHoliday
    Button(
        modifier = modifier
            .alpha(if (inThisMonth) 1f else 0.25f)
            .drawBehind {
                if (isToday) drawCircle(
                    color = Color.White,
                    radius = size.minDimension / 2.1f,
                    style = Stroke(width = 4f)
                )
            },
        onClick = { onDaySelected(dayValue) },
        shape = RectangleShape,
        colors = TRANSPARENT_BUTTON_COLORS,
        contentPadding = PaddingValues(0.dp),
        interactionSource = NO_RIPPLE_INTERACTION_SOURCE
    ) {
        Text(
            text = dayValue.toString(),
            fontFamily = MONTSERRAT_BOLD,
            fontSize = if (inThisMonth) schemes.size.font.main else schemes.size.font.dropdownItem,
            color = if (isWeekend || isHoliday) schemes.color.brightElement else Color.White,
            textAlign = TextAlign.Center
        )
    }
}
