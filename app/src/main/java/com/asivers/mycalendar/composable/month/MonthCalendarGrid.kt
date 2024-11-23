package com.asivers.mycalendar.composable.month

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.DAY_OF_WEEK_NAMES_LIST_3
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.constants.MONTSERRAT
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_RIPPLE_INTERACTION_SOURCE
import com.asivers.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.data.scheme.size.SizeScheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayValueForMonthTableElement
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getMonthViewBackgroundGradient
import com.asivers.mycalendar.utils.getSizeScheme
import com.asivers.mycalendar.utils.isHoliday

@Preview(showBackground = true)
@Composable
fun MonthCalendarGridPreview() {
    Box(
        modifier = Modifier
            .background(
                brush = getMonthViewBackgroundGradient(SUMMER)
            )
            .fillMaxWidth()
    ) {
        MonthCalendarGrid(
            monthInfo = getMonthInfo(
                getCurrentYear(),
                getCurrentMonthIndex(),
                DEFAULT_HOLIDAYS_INFO
            ),
            colorScheme = SUMMER,
            sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)
        )
    }
}

@Composable
fun MonthCalendarGrid(
    modifier: Modifier = Modifier,
    monthInfo: MonthInfo,
    colorScheme: ColorScheme,
    sizeScheme: SizeScheme
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp, 0.dp)
    ) {
        HeaderWeekInMonthCalendarGrid(
            sizeScheme = sizeScheme
        )
        repeat(6) { weekIndex ->
            WeekInMonthCalendarGrid(
                weekIndex = weekIndex,
                monthInfo = monthInfo,
                colorScheme = colorScheme,
                sizeScheme = sizeScheme
            )
        }
    }
}

@Composable
fun HeaderWeekInMonthCalendarGrid(
    modifier: Modifier = Modifier,
    sizeScheme: SizeScheme
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(7) { dayOfWeekIndex ->
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 5.dp),
                text = DAY_OF_WEEK_NAMES_LIST_3[dayOfWeekIndex],
                fontFamily = MONTSERRAT,
                fontSize = sizeScheme.font.mvHeaderWeek,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WeekInMonthCalendarGrid(
    modifier: Modifier = Modifier,
    weekIndex: Int,
    monthInfo: MonthInfo,
    colorScheme: ColorScheme,
    sizeScheme: SizeScheme
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
                weekIndex = weekIndex,
                dayOfWeekIndex = dayOfWeekIndex,
                monthInfo = monthInfo,
                colorScheme = colorScheme,
                sizeScheme = sizeScheme
            )
        }
    }
}

@Composable
fun DayInMonthCalendarGrid(
    modifier: Modifier = Modifier,
    weekIndex: Int,
    dayOfWeekIndex: Int,
    monthInfo: MonthInfo,
    colorScheme: ColorScheme,
    sizeScheme: SizeScheme
) {
    val dayValue = getDayValueForMonthTableElement(
        weekIndex,
        dayOfWeekIndex,
        monthInfo.numberOfDays,
        monthInfo.dayOfWeekOf1st
    )
    val today = dayValue !== null && dayValue === monthInfo.today
    val holiday = isHoliday(dayValue, dayOfWeekIndex, monthInfo.holidays)
    Button(
        modifier = modifier
            .drawBehind { if (today) drawCircle(Color.White, style = Stroke(width = 4f)) },
        onClick = {},
        shape = RectangleShape,
        colors = TRANSPARENT_BUTTON_COLORS,
        contentPadding = PaddingValues(0.dp),
        interactionSource = NO_RIPPLE_INTERACTION_SOURCE
    ) {
        Text(
            text = (dayValue ?: "").toString(),
            fontFamily = MONTSERRAT_BOLD,
            fontSize = sizeScheme.font.main,
            color = if (holiday) colorScheme.mvBtnLight else Color.White,
            textAlign = TextAlign.Center
        )
    }
}
