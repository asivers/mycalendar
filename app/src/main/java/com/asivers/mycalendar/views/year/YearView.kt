package com.asivers.mycalendar.views.year

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.data.HolidaysInfo
import com.asivers.mycalendar.ui.theme.custom.CustomColor
import com.asivers.mycalendar.utils.getCurrentYear

@Preview(showBackground = true)
@Composable
fun YearViewContentPreview() {
    YearViewContent(
        modifier = Modifier,
        year = getCurrentYear(),
        holidaysInfo = DEFAULT_HOLIDAYS_INFO,
        showYearView = remember { mutableStateOf(true) }
    )
}

@Composable
fun YearViewContent(
    modifier: Modifier,
    year: Int,
    holidaysInfo: HolidaysInfo,
    showYearView: MutableState<Boolean>
) {
    var offset by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier
            .background(color = CustomColor.MV_GRADIENT_BOTTOM)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = { offset = 0f },
                    onDragEnd = {
                        if (offset > 20f) {
                            showYearView.value = false
                        }
                    }
                ) { _, dragAmount ->
                    offset += dragAmount
                }
            }
    ) {
        YearCalendarGrid(
            year = year,
            holidaysInfo = holidaysInfo
        )
    }
}
