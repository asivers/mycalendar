package com.asivers.mycalendar.views.month

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asivers.mycalendar.constants.DAY_OF_WEEK_NAMES_LIST_3
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.constants.MONTH_VIEW_BACKGROUND_GRADIENT
import com.asivers.mycalendar.constants.NO_RIPPLE_INTERACTION_SOURCE
import com.asivers.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.ui.theme.custom.CustomColor
import com.asivers.mycalendar.ui.theme.custom.CustomFont
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayValueForMonthTableElement
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getTextColor

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
        HeaderWeekInMonthCalendarGrid()
        repeat(6) { weekIndex ->
            WeekInMonthCalendarGrid(
                weekIndex = weekIndex,
                monthInfo = monthInfo
            )
        }
    }
}

@Composable
fun HeaderWeekInMonthCalendarGrid() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(7) { dayOfWeekIndex ->
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 5.dp),
                text = DAY_OF_WEEK_NAMES_LIST_3[dayOfWeekIndex],
                fontFamily = CustomFont.MONTSERRAT,
                fontSize = 12.sp,
                color = CustomColor.WHITE,
                textAlign = TextAlign.Center,
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
            .padding(0.dp, 3.dp)
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
    val today = dayValue !== null && dayValue === monthInfo.today
    Button(
        modifier = modifier
            .drawBehind { if (today) drawCircle(CustomColor.WHITE, style = Stroke(width = 4f)) },
        onClick = {},
        shape = RectangleShape,
        colors = TRANSPARENT_BUTTON_COLORS,
        contentPadding = PaddingValues(0.dp),
        interactionSource = NO_RIPPLE_INTERACTION_SOURCE
    ) {
        Text(
            text = (dayValue ?: "").toString(),
            fontFamily = CustomFont.MONTSERRAT_BOLD,
            fontSize = 24.sp,
            color = getTextColor(dayValue, monthInfo.holidays, dayOfWeekIndex),
            textAlign = TextAlign.Center,
        )
    }
}
