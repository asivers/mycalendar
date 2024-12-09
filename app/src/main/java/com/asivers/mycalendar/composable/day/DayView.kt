package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.utils.PreviewFrameWithSettingsHeader
import com.asivers.mycalendar.utils.backToPreviousView
import com.asivers.mycalendar.utils.getCurrentDayOfMonth
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayViewBackgroundGradient
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getSchemesForPreview

@Preview(showBackground = true)
@Composable
fun DayViewPreview() {
    val schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    PreviewFrameWithSettingsHeader(
        viewShown = ViewShown.DAY,
        getBackground = { getDayViewBackgroundGradient(it) },
        schemes = schemes
    ) {
        DayView(
            selectedYearInfo = remember { mutableStateOf(SelectedYearInfo(getCurrentYear())) },
            selectedMonthInfo = remember { mutableStateOf(SelectedMonthInfo(getCurrentYear(), getCurrentMonthIndex())) },
            selectedDay = remember { mutableIntStateOf(getCurrentDayOfMonth()) },
            viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.MONTH)) },
            schemes = schemes
        )
    }
}

@Composable
fun DayView(
    modifier: Modifier = Modifier,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    selectedDay: MutableIntState,
    viewShownInfo: MutableState<ViewShownInfo>,
    schemes: SchemeContainer
) {
    val indentFromHeaderDp = getIndentFromHeaderDp(LocalConfiguration.current.screenHeightDp)
    var horizontalOffset by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(18.dp, indentFromHeaderDp.dp, 18.dp, 0.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        horizontalOffset = 0f
                    },
                    onDragEnd = {
                        if (horizontalOffset > 50f) {
                            backToPreviousView(viewShownInfo)
                        }
                    }
                ) { _, dragAmount ->
                    horizontalOffset += dragAmount
                }
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = selectedDay.intValue.toString(),
                fontFamily = MONTSERRAT_BOLD,
                fontSize = schemes.size.font.main,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
