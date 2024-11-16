package com.asivers.mycalendar.views.month

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.data.HolidaysInfo
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getMonthInfo

@Preview(showBackground = true)
@Composable
fun MonthViewContentPreview() {
    MonthViewContent(
        year = getCurrentYear(),
        monthIndex = getCurrentMonthIndex(),
        holidaysInfo = DEFAULT_HOLIDAYS_INFO,
        showYearView = remember { mutableStateOf(false) }
    )
}

@Composable
fun MonthViewContent(
    year: Int,
    monthIndex: Int,
    holidaysInfo: HolidaysInfo,
    showYearView: MutableState<Boolean>
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
        YearViewButton(showYearView = showYearView)
    }
}
