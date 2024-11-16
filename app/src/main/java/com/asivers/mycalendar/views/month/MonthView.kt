package com.asivers.mycalendar.views.month

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.asivers.mycalendar.data.HolidaysInfo
import com.asivers.mycalendar.constants.MONTH_VIEW_BACKGROUND_GRADIENT
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getMonthInfo

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
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier.weight(2f),
                    contentAlignment = Alignment.Center
                ) {
                    TopDropdowns()
                }
                Box(modifier = Modifier.weight(7f)) {
                    MonthCalendarGrid(
                        monthInfo = getMonthInfo(year, monthIndex, holidaysInfo)
                    )
                }
            }
            YearViewButton()
        }
    }
}
