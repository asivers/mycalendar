package com.example.mycalendar.views.month

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalendar.ui.theme.custom.CustomFont
import com.example.mycalendar.data.MonthInfo
import com.example.mycalendar.constants.MONTH_VIEW_BACKGROUND_GRADIENT
import com.example.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.example.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.example.mycalendar.utils.getCurrentMonthIndex
import com.example.mycalendar.utils.getCurrentYear
import com.example.mycalendar.utils.getDayValueForMonthTableElement
import com.example.mycalendar.utils.getMonthInfo
import com.example.mycalendar.utils.getTextColor

@Preview(showBackground = true)
@Composable
fun MonthCalendarGridPreview() {
    Box(
        modifier = Modifier
            .background(
                brush = MONTH_VIEW_BACKGROUND_GRADIENT
            )
            .fillMaxWidth()
    ) {
        MonthCalendarGrid(
            monthInfo = getMonthInfo(
                getCurrentYear(),
                getCurrentMonthIndex(),
                DEFAULT_HOLIDAYS_INFO
            )
        )
    }
}

@Composable
fun MonthCalendarGrid(
    monthInfo: MonthInfo
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(6) { weekIndex ->
            WeekInMonthCalendarGrid(
                weekIndex = weekIndex,
                monthInfo = monthInfo
            )
        }
    }
}

@Composable
fun WeekInMonthCalendarGrid(
    weekIndex: Int,
    monthInfo: MonthInfo
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        repeat(7) { dayOfWeekIndex ->
            DayInMonthCalendarGrid(
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
fun DayInMonthCalendarGrid(
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
    Button(
        modifier = modifier,
        onClick = {},
//        border = BorderStroke(1.dp, Color.Black),
        shape = RectangleShape,
        colors = TRANSPARENT_BUTTON_COLORS,
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = (dayValue ?: "").toString(),
            fontFamily = CustomFont.MONTSERRAT_BOLD,
            fontSize = 26.sp,
            color = getTextColor(dayValue, monthInfo.holidays, dayOfWeekIndex),
            textAlign = TextAlign.Center,
        )
    }
}