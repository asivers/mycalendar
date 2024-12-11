package com.asivers.mycalendar.composable.year

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_PADDING_TEXT_STYLE
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.changeView
import com.asivers.mycalendar.utils.fadeSlow
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayInfo
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.getYearViewBackgroundGradient
import com.asivers.mycalendar.utils.noRippleClickable

@Preview(showBackground = true)
@Composable
fun YearCalendarGridPreview() {
    Box(
        modifier = Modifier
            .background(getYearViewBackgroundGradient(SUMMER))
            .fillMaxWidth()
    ) {
        YearCalendarGrid(
            selectedYearInfo = remember { mutableStateOf(SelectedYearInfo(getCurrentYear())) },
            selectedMonthInfo = remember { mutableStateOf(SelectedMonthInfo(getCurrentYear(), getCurrentMonthIndex())) },
            viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.MONTH)) },
            weekendMode = WeekendMode.SATURDAY_SUNDAY,
            schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
        )
    }
}

@Composable
fun YearCalendarGrid(
    modifier: Modifier = Modifier,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    viewShownInfo: MutableState<ViewShownInfo>,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    Column(
        modifier = modifier.padding(0.dp, 4.dp),
    ) {
        repeat(4) { threeMonthRowIndex ->
            ThreeMonthsRowInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                selectedYearInfo = selectedYearInfo,
                selectedMonthInfo = selectedMonthInfo,
                viewShownInfo = viewShownInfo,
                threeMonthRowIndex = threeMonthRowIndex,
                weekendMode = weekendMode,
                schemes = schemes
            )
        }
    }
}

@Composable
fun ThreeMonthsRowInYearCalendarGrid(
    modifier: Modifier = Modifier,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    viewShownInfo: MutableState<ViewShownInfo>,
    threeMonthRowIndex: Int,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(3) { monthInRowIndex ->
            MonthInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                selectedYearInfo = selectedYearInfo,
                selectedMonthInfo = selectedMonthInfo,
                viewShownInfo = viewShownInfo,
                thisMonthIndex = threeMonthRowIndex * 3 + monthInRowIndex,
                weekendMode = weekendMode,
                schemes = schemes
            )
        }
    }
}

@Composable
fun MonthInYearCalendarGrid(
    modifier: Modifier = Modifier,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    viewShownInfo: MutableState<ViewShownInfo>,
    thisMonthIndex: Int,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val thisYear = selectedYearInfo.value.year
    val isLastSelectedMonth = thisYear == selectedMonthInfo.value.year
            && thisMonthIndex == selectedMonthInfo.value.monthIndex
    val background = if (isLastSelectedMonth) schemes.color.monthViewTop else Color.Transparent
    Column(
        modifier = modifier
            .padding(schemes.size.horizontal.yvMonthPadding, schemes.size.vertical.yvMonthPadding)
            .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
            .background(background)
            .padding(4.dp, 2.dp)
            .noRippleClickable {
                selectedMonthInfo.value = SelectedMonthInfo(thisYear, thisMonthIndex)
                changeView(viewShownInfo, ViewShown.MONTH)
            }
    ) {
        Text(
            text = schemes.translation.months[thisMonthIndex],
            modifier = Modifier.padding(3.dp, 0.dp),
            fontFamily = MONTSERRAT_BOLD,
            fontSize = schemes.size.font.yvMonthName,
            color = Color.White
        )
        HeaderWeekInYearCalendarGrid(
            modifier = Modifier
                .wrapContentHeight()
                .padding(0.dp, 5.dp, 0.dp, 3.dp),
            schemes = schemes
        )
        AnimatedContent(
            targetState = selectedYearInfo.value,
            transitionSpec = { fadeSlow() },
            label = "year calendar animated content"
        ) {
            val monthInfo = getMonthInfo(
                year = it.year,
                monthIndex = thisMonthIndex,
                countryHolidayScheme = schemes.countryHoliday,
                forYearView = true
            )
            Column {
                repeat(6) { weekIndex ->
                    WeekInYearCalendarGrid(
                        modifier = Modifier.weight(1f),
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
fun HeaderWeekInYearCalendarGrid(
    modifier: Modifier = Modifier,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(7) { dayOfWeekIndex ->
            Text(
                modifier = Modifier.weight(1f),
                text = schemes.translation.daysOfWeek1[dayOfWeekIndex],
                fontFamily = MONTSERRAT,
                fontSize = schemes.size.font.yvHeaderWeek,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = NO_PADDING_TEXT_STYLE
            )
        }
    }
}

@Composable
fun WeekInYearCalendarGrid(
    modifier: Modifier = Modifier,
    weekIndex: Int,
    monthInfo: MonthInfo,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(7) { dayOfWeekIndex ->
            DayInYearCalendarGrid(
                modifier = Modifier.weight(1f),
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
fun DayInYearCalendarGrid(
    modifier: Modifier = Modifier,
    weekIndex: Int,
    dayOfWeekIndex: Int,
    monthInfo: MonthInfo,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val dayValueRaw = weekIndex * 7 + dayOfWeekIndex - monthInfo.dayOfWeekOf1st + 1
    val dayInfo = getDayInfo(dayValueRaw, monthInfo, weekendMode)
    val dayValue = dayInfo.dayValue
    val inThisMonth = dayInfo.inThisMonth
    val isToday = dayInfo.isToday
    val isWeekend = dayInfo.isWeekend
    val isHoliday = dayInfo.isHoliday
    Text(
        modifier = modifier
            .fillMaxSize()
            .drawBehind { if (isToday) drawCircle(
                color = Color.White,
                style = Stroke(width = 2f),
                center = this.center.plus(Offset(x = -1f, y = 0f))
            )}
            .wrapContentHeight(),
        text = (if (inThisMonth) dayValue else "").toString(),
        fontFamily = MONTSERRAT_BOLD,
        fontSize = schemes.size.font.yvDay,
        color = if (isWeekend || isHoliday) schemes.color.brightElement else Color.White,
        textAlign = TextAlign.Center,
        style = NO_PADDING_TEXT_STYLE
    )
}
