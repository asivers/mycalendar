package com.asivers.mycalendar.views.year

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
        year = remember { mutableIntStateOf(getCurrentYear()) },
        showYearView = remember { mutableStateOf(true) },
        holidaysInfo = DEFAULT_HOLIDAYS_INFO
    )
}

@Composable
fun YearViewContent(
    modifier: Modifier,
    year: MutableIntState,
    showYearView: MutableState<Boolean>,
    holidaysInfo: HolidaysInfo
) {
    var horizontalOffset by remember { mutableFloatStateOf(0f) }
    var verticalOffset by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier
            .background(color = CustomColor.MV_GRADIENT_BOTTOM)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        horizontalOffset = 0f
                        verticalOffset = 0f
                    },
                    onDragEnd = {
                        if (verticalOffset > 100f) {
                            showYearView.value = false
                        } else if (horizontalOffset > 100f) {
                            year.intValue++
                        } else if (horizontalOffset < -100f) {
                            year.intValue--
                        }
                    }
                ) { _, dragAmount ->
                    horizontalOffset += dragAmount.x
                    verticalOffset += dragAmount.y
                }
            }
    ) {
        YearCalendarGrid(
            year = year.intValue,
            holidaysInfo = holidaysInfo
        )
    }
}
