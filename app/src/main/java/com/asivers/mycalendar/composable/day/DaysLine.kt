package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_RIPPLE_INTERACTION_SOURCE
import com.asivers.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.getDayInfo

@Composable
fun DaysLine(
    modifier: Modifier = Modifier,
    onDayChanged: (Int, Boolean) -> Unit,
    selectedDay: Int,
    thisMonthInfo: MonthInfo,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(0.dp, 3.dp)
    ) {
        repeat(7) {
            val dayValueRaw = selectedDay + it - 3
            val dayInfo = getDayInfo(dayValueRaw, thisMonthInfo, weekendMode)
            val dayValue = dayInfo.dayValue
            val inThisMonth = dayInfo.inThisMonth
            val isToday = dayInfo.isToday
            val isWeekend = dayInfo.isWeekend
            val isHoliday = dayInfo.isHoliday
            Button(
                modifier = Modifier
                    .weight(1f)
                    .alpha(if (it == 3) 1f else 0.5f)
                    .drawBehind {
                        if (isToday) drawCircle(
                            color = Color.White,
                            radius = size.minDimension / 2.1f,
                            style = Stroke(width = 4f)
                        )
                    },
                onClick = { onDayChanged(dayValue, inThisMonth) },
                shape = RectangleShape,
                colors = TRANSPARENT_BUTTON_COLORS,
                contentPadding = PaddingValues(0.dp),
                interactionSource = NO_RIPPLE_INTERACTION_SOURCE
            ) {
                Text(
                    text = dayValue.toString(),
                    fontFamily = MONTSERRAT_BOLD,
                    fontSize = if (it == 3) schemes.size.font.main else schemes.size.font.dropdownItem,
                    color = if (isWeekend || isHoliday) schemes.color.brightElement else Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}