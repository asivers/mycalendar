package com.asivers.mycalendar.views.year

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asivers.mycalendar.data.HolidaysInfo
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.ui.theme.custom.CustomColor
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.ui.theme.custom.CustomFont
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayValueForMonthTableElement
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getTextColor

@Preview(showBackground = true)
@Composable
fun YearCalendarGridPreview() {
    Box(
        modifier = Modifier
            .background(color = CustomColor.MV_GRADIENT_BOTTOM)
            .fillMaxWidth()
    ) {
        YearCalendarGrid(
            year = getCurrentYear(),
            holidaysInfo = DEFAULT_HOLIDAYS_INFO
        )
    }
}

@Composable
fun YearCalendarGrid(
    year: Int,
    holidaysInfo: HolidaysInfo
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        repeat(4) { threeMonthRowIndex ->
            ThreeMonthsRowInYearCalendarGrid(year, threeMonthRowIndex, holidaysInfo)
        }
    }
}

@Composable
fun ThreeMonthsRowInYearCalendarGrid(
    year: Int,
    threeMonthRowIndex: Int,
    holidaysInfo: HolidaysInfo
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        repeat(3) { monthInRowIndex ->
            MonthInYearCalendarGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                year = year,
                monthIndex = threeMonthRowIndex * 3 + monthInRowIndex,
                holidaysInfo = holidaysInfo
            )
        }
    }
}

@Composable
fun MonthInYearCalendarGrid(
    modifier: Modifier,
    year: Int,
    monthIndex: Int,
    holidaysInfo: HolidaysInfo
) {
    Column(
        modifier = modifier
    ) {
        repeat(6) { weekIndex ->
            WeekInYearCalendarGrid(
                weekIndex = weekIndex,
                monthInfo = getMonthInfo(year, monthIndex, holidaysInfo)
            )
        }
    }
}

@Composable
fun WeekInYearCalendarGrid(
    weekIndex: Int,
    monthInfo: MonthInfo
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        repeat(7) { dayOfWeekIndex ->
            DayInYearCalendarGrid(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
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
    Text(
        modifier = modifier,
        text = (dayValue ?: "").toString(),
        fontFamily = CustomFont.MONTSERRAT_BOLD,
        fontSize = 12.sp,
        color = getTextColor(dayValue, monthInfo.holidays, dayOfWeekIndex),
        textAlign = TextAlign.Center,
    )
}
