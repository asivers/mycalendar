package com.asivers.mycalendar.composable.month

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.day.INDEX_OF_SELECTED_DAY
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_RIPPLE_INTERACTION_SOURCE
import com.asivers.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.asivers.mycalendar.data.DayInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.DisplayedMonth

@Composable
fun DayWithNoteMark(
    modifier: Modifier = Modifier,
    onDaySelected: (Int, DisplayedMonth) -> Unit,
    dayInfo: DayInfo,
    schemes: SchemeContainer,
    orderInDayLine: Int? = null
) {
    val dayValue = dayInfo.dayValue
    val inMonth = dayInfo.inMonth
    val isToday = dayInfo.isToday
    val isWeekend = dayInfo.isWeekend
    val isHoliday = dayInfo.isHoliday
    val isWithNote = dayInfo.isWithNote

    val isForMonthView = orderInDayLine == null
    val isPrimaryDay = isForMonthView && inMonth == DisplayedMonth.THIS
            || orderInDayLine == INDEX_OF_SELECTED_DAY

    Button(
        modifier = modifier
            .alpha(if (isPrimaryDay) 1f else if (isForMonthView) 0.25f else 0.5f)
            .drawBehind {
                if (isToday) drawCircle(
                    color = schemes.color.text,
                    radius = size.minDimension / 2.1f,
                    style = Stroke(width = 4f)
                )
            },
        onClick = { onDaySelected(dayValue, inMonth) },
        shape = RectangleShape,
        colors = TRANSPARENT_BUTTON_COLORS,
        contentPadding = PaddingValues(0.dp),
        interactionSource = NO_RIPPLE_INTERACTION_SOURCE
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dayValue.toString(),
                fontFamily = MONTSERRAT_BOLD,
                fontSize = if (isPrimaryDay) schemes.size.font.main else schemes.size.font.dropdownItem,
                color = if (isWeekend || isHoliday) schemes.color.brightElement else schemes.color.text,
                textAlign = TextAlign.Center
            )
            if (isWithNote) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(schemes.color.text, CircleShape)
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
