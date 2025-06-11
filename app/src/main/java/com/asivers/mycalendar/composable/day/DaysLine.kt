package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.month.DayWithNoteMark
import com.asivers.mycalendar.constants.MONTSERRAT
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.DisplayedMonth
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.date.getDayInfo
import com.asivers.mycalendar.utils.getScreenWidthDp

const val INDEX_OF_SELECTED_DAY = 28
private const val INDEX_OF_FIRST_VISIBLE_DAY = INDEX_OF_SELECTED_DAY - 3

private const val NUMBER_OF_ITEMS_TOTAL = INDEX_OF_SELECTED_DAY * 2 + 1
private const val NUMBER_OF_ITEMS_VISIBLE = 7

@Composable
fun DaysLine(
    modifier: Modifier = Modifier,
    onDayChanged: (Int, DisplayedMonth) -> Unit,
    onSlide: (Int) -> Unit,
    selectedDay: Int,
    thisMonthInfo: MonthInfo,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val density = LocalDensity.current
    val screenWidthDp = getScreenWidthDp(ctx, density)
    val itemWidth = (screenWidthDp / NUMBER_OF_ITEMS_VISIBLE).dp

    val wasScrollingInitiatedState = remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState(INDEX_OF_FIRST_VISIBLE_DAY)

    LaunchedEffect(scrollState.isScrollInProgress) {
        if (!scrollState.isScrollInProgress && wasScrollingInitiatedState.value) {
            onSlide(scrollState.firstVisibleItemIndex - INDEX_OF_FIRST_VISIBLE_DAY + 1)
        }
        wasScrollingInitiatedState.value = scrollState.isScrollInProgress
    }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = scrollState,
        flingBehavior = rememberSnapFlingBehavior(scrollState)
    ) {
        items(
            count = NUMBER_OF_ITEMS_TOTAL,
            itemContent = { orderInDayLine ->
                val dayValueRaw = selectedDay + orderInDayLine - INDEX_OF_SELECTED_DAY
                val dayInfo = getDayInfo(dayValueRaw, thisMonthInfo, weekendMode)
                Column(
                    modifier = Modifier.width(itemWidth),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val alpha = if (orderInDayLine == INDEX_OF_SELECTED_DAY) 1f else 0.5f
                    Text(
                        modifier = Modifier.alpha(alpha),
                        text = schemes.translation.daysOfWeek3[dayInfo.dayOfWeekValue - 1],
                        fontFamily = MONTSERRAT,
                        fontSize = schemes.size.font.mvHeaderWeek,
                        color = schemes.color.text,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DayWithNoteMark(
                        onDaySelected = onDayChanged,
                        dayInfo = dayInfo,
                        schemes = schemes,
                        orderInDayLine = orderInDayLine
                    )
                }
            }
        )
    }
}
