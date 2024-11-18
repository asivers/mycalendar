package com.asivers.mycalendar.views.year

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asivers.mycalendar.constants.DAY_OF_WEEK_NAMES_LIST_1
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.constants.MONTH_NAMES_LIST
import com.asivers.mycalendar.constants.NO_PADDING_TEXT_STYLE
import com.asivers.mycalendar.constants.YEAR_VIEW_BACKGROUND_GRADIENT
import com.asivers.mycalendar.data.HolidaysInfo
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.ui.theme.custom.CustomColor
import com.asivers.mycalendar.ui.theme.custom.CustomFont
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayValueForMonthTableElement
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getTextColor
import com.asivers.mycalendar.utils.noRippleClickable

@Preview(showBackground = true)
@Composable
fun YearCalendarGridPreview() {
    Box(
        modifier = Modifier
            .background(YEAR_VIEW_BACKGROUND_GRADIENT)
            .fillMaxWidth()
    ) {
        YearCalendarGrid(
            thisYear = getCurrentYear(),
            selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
            showYearView = remember { mutableStateOf(true) },
            lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) },
            holidaysInfo = DEFAULT_HOLIDAYS_INFO
        )
    }
}

@Composable
fun YearCalendarGrid(
    thisYear: Int,
    selectedMonthIndex: MutableIntState,
    showYearView: MutableState<Boolean>,
    lastSelectedYearFromMonthView: MutableIntState,
    holidaysInfo: HolidaysInfo
) {
    Column(
        modifier = Modifier.padding(0.dp, 4.dp),
    ) {
        repeat(4) { threeMonthRowIndex ->
            ThreeMonthsRowInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                thisYear = thisYear,
                threeMonthRowIndex = threeMonthRowIndex,
                selectedMonthIndex = selectedMonthIndex,
                showYearView = showYearView,
                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                holidaysInfo = holidaysInfo
            )
        }
    }
}

@Composable
fun ThreeMonthsRowInYearCalendarGrid(
    modifier: Modifier,
    thisYear: Int,
    threeMonthRowIndex: Int,
    selectedMonthIndex: MutableIntState,
    showYearView: MutableState<Boolean>,
    lastSelectedYearFromMonthView: MutableIntState,
    holidaysInfo: HolidaysInfo
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(3) { monthInRowIndex ->
            MonthInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                thisYear = thisYear,
                thisMonthIndex = threeMonthRowIndex * 3 + monthInRowIndex,
                selectedMonthIndex = selectedMonthIndex,
                showYearView = showYearView,
                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                holidaysInfo = holidaysInfo
            )
        }
    }
}

@Composable
fun MonthInYearCalendarGrid(
    modifier: Modifier,
    thisYear: Int,
    thisMonthIndex: Int,
    selectedMonthIndex: MutableIntState,
    showYearView: MutableState<Boolean>,
    lastSelectedYearFromMonthView: MutableIntState,
    holidaysInfo: HolidaysInfo
) {
    val isLastSelectedMonth = thisYear == lastSelectedYearFromMonthView.intValue
            && thisMonthIndex == selectedMonthIndex.intValue
    val background = if (isLastSelectedMonth)
        CustomColor.YV_LAST_SELECTED_MONTH else CustomColor.TRANSPARENT
    Column(
        modifier = modifier
            .background(background)
            .padding(7.dp, 5.dp)
            .noRippleClickable {
                selectedMonthIndex.intValue = thisMonthIndex
                showYearView.value = false
            }
    ) {
        Text(
            text = MONTH_NAMES_LIST[thisMonthIndex],
            modifier = Modifier.padding(3.dp, 0.dp),
            fontFamily = CustomFont.MONTSERRAT_BOLD,
            fontSize = 14.sp,
            color = CustomColor.WHITE,
        )
        HeaderWeekInYearCalendarGrid(
            modifier = Modifier.weight(1f)
        )
        repeat(6) { weekIndex ->
            WeekInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                weekIndex = weekIndex,
                monthInfo = getMonthInfo(thisYear, thisMonthIndex, holidaysInfo)
            )
        }
    }
}

@Composable
fun HeaderWeekInYearCalendarGrid(
    modifier: Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(7) { dayOfWeekIndex ->
            Text(
                modifier = Modifier.weight(1f),
                text = DAY_OF_WEEK_NAMES_LIST_1[dayOfWeekIndex],
                fontFamily = CustomFont.MONTSERRAT,
                fontSize = 7.sp,
                color = CustomColor.WHITE,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun WeekInYearCalendarGrid(
    modifier: Modifier,
    weekIndex: Int,
    monthInfo: MonthInfo
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(7) { dayOfWeekIndex ->
            DayInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                weekIndex = weekIndex,
                dayOfWeekIndex = dayOfWeekIndex,
                monthInfo = monthInfo
            )
        }
    }
}

@Composable
fun DayInYearCalendarGrid(
    modifier: Modifier,
    weekIndex: Int,
    dayOfWeekIndex: Int,
    monthInfo: MonthInfo
) {
    val dayValue = getDayValueForMonthTableElement(
        weekIndex,
        dayOfWeekIndex,
        monthInfo.numberOfDays,
        monthInfo.dayOfWeekOf1st
    )
    val today = dayValue !== null && dayValue === monthInfo.today
    Text(
        modifier = modifier
            .fillMaxSize()
            .drawBehind { if (today) drawCircle(CustomColor.WHITE, style = Stroke(width = 3f)) }
            .wrapContentHeight(),
        text = (dayValue ?: "").toString(),
        fontFamily = CustomFont.MONTSERRAT_BOLD,
        fontSize = 9.sp,
        color = getTextColor(dayValue, monthInfo.holidays, dayOfWeekIndex),
        textAlign = TextAlign.Center,
        style = NO_PADDING_TEXT_STYLE
    )
}
