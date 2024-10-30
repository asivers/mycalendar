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
import com.example.mycalendar.utils.defaultHolidaysInfo

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
