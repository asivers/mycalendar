package com.asivers.mycalendar.views.year

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.asivers.mycalendar.data.HolidaysInfo
import com.asivers.mycalendar.ui.theme.custom.CustomColor
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.utils.getCurrentYear

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
            .background(color = CustomColor.MV_GRADIENT_BOTTOM)
            .fillMaxWidth()
    ) {
        YearCalendarGrid(
            year = year,
            holidaysInfo = holidaysInfo
        )
    }
}
