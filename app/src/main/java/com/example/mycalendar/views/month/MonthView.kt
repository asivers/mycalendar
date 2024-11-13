package com.example.mycalendar.views.month

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalendar.data.HolidaysInfo
import com.example.mycalendar.constants.MONTH_VIEW_BACKGROUND_GRADIENT
import com.example.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.example.mycalendar.utils.getCurrentMonthIndex
import com.example.mycalendar.utils.getCurrentYear
import com.example.mycalendar.utils.getMonthInfo

@Preview(showBackground = true)
@Composable
fun MonthViewContentPreview() {
    MonthViewContent(
        modifier = Modifier,
        year = getCurrentYear(),
        monthIndex = getCurrentMonthIndex(),
        holidaysInfo = DEFAULT_HOLIDAYS_INFO
    )
}

@Composable
fun MonthViewContent(
    modifier: Modifier,
    year: Int,
    monthIndex: Int,
    holidaysInfo: HolidaysInfo
) {
    Column(
        modifier = modifier
            .background(
                brush = MONTH_VIEW_BACKGROUND_GRADIENT
            )
            .fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(50.dp))
            TopDropdowns()
            Spacer(modifier = Modifier.height(50.dp))
            MonthCalendarGrid(
                monthInfo = getMonthInfo(year, monthIndex, holidaysInfo)
            )
            Spacer(modifier = Modifier.weight(1f))
            YearViewButton()
        }
    }
}
