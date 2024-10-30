package com.example.mycalendar.views.month

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycalendar.data.HolidaysInfo
import com.example.mycalendar.ui.theme.CustomColor
import com.example.mycalendar.utils.defaultHolidaysInfo
import com.example.mycalendar.utils.getMonthInfo
import java.util.Calendar.OCTOBER

@Preview(showBackground = true)
@Composable
fun MonthViewContentOctober2024() {
    MonthViewContent(
        modifier = Modifier,
        year = 2024,
        monthIndex = OCTOBER,
        holidaysInfo = defaultHolidaysInfo
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
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to CustomColor.Mv_gradient_top,
                        0.1f to CustomColor.Mv_gradient_top,
                        0.25f to CustomColor.Mv_gradient_bottom,
                        1f to CustomColor.Mv_gradient_bottom,
                    )
                )
            )
            .fillMaxWidth()
    ) {
        TopDropdowns()
        MonthCalendarGrid(
            monthInfo = getMonthInfo(year, monthIndex, holidaysInfo)
        )
        YearViewButton()
    }
}
