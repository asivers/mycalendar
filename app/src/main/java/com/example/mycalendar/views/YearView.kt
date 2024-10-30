package com.example.mycalendar.views

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalendar.data.HolidaysInfo
import com.example.mycalendar.data.MonthInfo
import com.example.mycalendar.ui.theme.CustomColor
import com.example.mycalendar.utils.defaultHolidaysInfo
import com.example.mycalendar.utils.getDayValueForMonthTableElement
import com.example.mycalendar.utils.getMonthInfo
import com.example.mycalendar.utils.getTextColor

@Preview(showBackground = true)
@Composable
fun YearViewContent2024() {
    Box(modifier = Modifier.fillMaxWidth()) {
        YearViewContent(
            modifier = Modifier,
            year = 2024,
            holidaysInfo = defaultHolidaysInfo
        )
    }
}

@Composable
fun YearViewContent(
    modifier: Modifier,
    year: Int,
    holidaysInfo: HolidaysInfo
) {
    Column(
        modifier = modifier
            .background(color = CustomColor.Mv_gradient_bottom)
            .fillMaxWidth()
    ) {
        YearCalendarGrid(
            year = year,
            holidaysInfo = holidaysInfo
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
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = getTextColor(dayValue, monthInfo.holidays, dayOfWeekIndex),
        textAlign = TextAlign.Center,
    )
}
