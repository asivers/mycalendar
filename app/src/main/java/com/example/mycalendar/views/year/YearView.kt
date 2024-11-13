package com.example.mycalendar.views.year

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycalendar.data.HolidaysInfo
import com.example.mycalendar.ui.theme.CustomColor
import com.example.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.example.mycalendar.utils.getCurrentYear

@Preview(showBackground = true)
@Composable
fun YearViewContentPreview() {
    Box(modifier = Modifier.fillMaxWidth()) {
        YearViewContent(
            modifier = Modifier,
            year = getCurrentYear(),
            holidaysInfo = DEFAULT_HOLIDAYS_INFO
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
