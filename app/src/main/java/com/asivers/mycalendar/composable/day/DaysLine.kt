package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.month.DayWithNoteMark
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.getDayInfo
import com.asivers.mycalendar.utils.onHorizontalSwipe
import kotlin.math.roundToInt

@Composable
fun DaysLine(
    modifier: Modifier = Modifier,
    onDayChanged: (Int, DisplayedMonth) -> Unit,
    onSwipe: (Int) -> Unit,
    selectedDay: Int,
    thisMonthInfo: MonthInfo,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val screenWidthPx = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val minimumSwipe = screenWidthPx / 7
    val horizontalOffset = remember { mutableFloatStateOf(0f) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(0.dp, 3.dp)
            .onHorizontalSwipe(
                horizontalOffset = horizontalOffset,
                onSwipeToLeft = { onSwipe(-(horizontalOffset.floatValue / minimumSwipe).roundToInt()) },
                minimumSwipe = minimumSwipe
            )
    ) {
        repeat(7) {
            val dayValueRaw = selectedDay + it - 3
            val dayInfo = getDayInfo(dayValueRaw, thisMonthInfo, weekendMode)
            DayWithNoteMark(
                modifier = Modifier.weight(1f),
                onDaySelected = onDayChanged,
                dayInfo = dayInfo,
                schemes = schemes
            )
        }
    }
}
