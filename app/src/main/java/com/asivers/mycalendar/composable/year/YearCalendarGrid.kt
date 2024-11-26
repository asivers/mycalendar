package com.asivers.mycalendar.composable.year

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_PADDING_TEXT_STYLE
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.scheme.CountryHolidaysScheme
import com.asivers.mycalendar.data.MonthInfo
import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.data.scheme.TranslationsScheme
import com.asivers.mycalendar.data.scheme.size.SizeScheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getDayValueForMonthTableElement
import com.asivers.mycalendar.utils.getCountryHolidaysSchemeForPreview
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getSizeScheme
import com.asivers.mycalendar.utils.getTranslationsSchemeForPreview
import com.asivers.mycalendar.utils.getYearViewBackgroundGradient
import com.asivers.mycalendar.utils.isHoliday
import com.asivers.mycalendar.utils.noRippleClickable

@Preview(showBackground = true)
@Composable
fun YearCalendarGridPreview() {
    Box(
        modifier = Modifier
            .background(getYearViewBackgroundGradient(SUMMER))
            .fillMaxWidth()
    ) {
        YearCalendarGrid(
            thisYear = getCurrentYear(),
            selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
            showYearView = remember { mutableStateOf(true) },
            lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) },
            countryHolidaysScheme = getCountryHolidaysSchemeForPreview(),
            colorScheme = SUMMER,
            translationsScheme = getTranslationsSchemeForPreview(),
            sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)
        )
    }
}

@Composable
fun YearCalendarGrid(
    modifier: Modifier = Modifier,
    thisYear: Int,
    selectedMonthIndex: MutableIntState,
    showYearView: MutableState<Boolean>,
    lastSelectedYearFromMonthView: MutableIntState,
    countryHolidaysScheme: CountryHolidaysScheme,
    colorScheme: ColorScheme,
    translationsScheme: TranslationsScheme,
    sizeScheme: SizeScheme
) {
    Column(
        modifier = modifier.padding(0.dp, 4.dp),
    ) {
        repeat(4) { threeMonthRowIndex ->
            ThreeMonthsRowInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                thisYear = thisYear,
                threeMonthRowIndex = threeMonthRowIndex,
                selectedMonthIndex = selectedMonthIndex,
                showYearView = showYearView,
                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                countryHolidaysScheme = countryHolidaysScheme,
                colorScheme = colorScheme,
                translationsScheme = translationsScheme,
                sizeScheme = sizeScheme
            )
        }
    }
}

@Composable
fun ThreeMonthsRowInYearCalendarGrid(
    modifier: Modifier = Modifier,
    thisYear: Int,
    threeMonthRowIndex: Int,
    selectedMonthIndex: MutableIntState,
    showYearView: MutableState<Boolean>,
    lastSelectedYearFromMonthView: MutableIntState,
    countryHolidaysScheme: CountryHolidaysScheme,
    colorScheme: ColorScheme,
    translationsScheme: TranslationsScheme,
    sizeScheme: SizeScheme
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(3) { monthInRowIndex ->
            MonthInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                thisYear = thisYear,
                thisMonthIndex = threeMonthRowIndex * 3 + monthInRowIndex,
                selectedMonthIndex = selectedMonthIndex,
                showYearView = showYearView,
                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                countryHolidaysScheme = countryHolidaysScheme,
                colorScheme = colorScheme,
                translationsScheme = translationsScheme,
                sizeScheme = sizeScheme
            )
        }
    }
}

@Composable
fun MonthInYearCalendarGrid(
    modifier: Modifier = Modifier,
    thisYear: Int,
    thisMonthIndex: Int,
    selectedMonthIndex: MutableIntState,
    showYearView: MutableState<Boolean>,
    lastSelectedYearFromMonthView: MutableIntState,
    countryHolidaysScheme: CountryHolidaysScheme,
    colorScheme: ColorScheme,
    translationsScheme: TranslationsScheme,
    sizeScheme: SizeScheme
) {
    val isLastSelectedMonth = thisYear == lastSelectedYearFromMonthView.intValue
            && thisMonthIndex == selectedMonthIndex.intValue
    val background = if (isLastSelectedMonth) colorScheme.mvLight else Color.Transparent
    Column(
        modifier = modifier
            .padding(sizeScheme.horizontal.yvMonthPadding, sizeScheme.vertical.yvMonthPadding)
            .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
            .background(background)
            .padding(4.dp, 2.dp)
            .noRippleClickable {
                selectedMonthIndex.intValue = thisMonthIndex
                lastSelectedYearFromMonthView.intValue = thisYear
                showYearView.value = false
            }
    ) {
        Text(
            text = translationsScheme.months[thisMonthIndex],
            modifier = Modifier.padding(3.dp, 0.dp),
            fontFamily = MONTSERRAT_BOLD,
            fontSize = sizeScheme.font.yvMonthName,
            color = Color.White
        )
        HeaderWeekInYearCalendarGrid(
            modifier = Modifier
                .wrapContentHeight()
                .padding(0.dp, 5.dp, 0.dp, 3.dp),
            translationsScheme = translationsScheme,
            sizeScheme = sizeScheme
        )
        repeat(6) { weekIndex ->
            WeekInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                weekIndex = weekIndex,
                monthInfo = getMonthInfo(thisYear, thisMonthIndex, countryHolidaysScheme),
                colorScheme = colorScheme,
                sizeScheme = sizeScheme
            )
        }
    }
}

@Composable
fun HeaderWeekInYearCalendarGrid(
    modifier: Modifier = Modifier,
    translationsScheme: TranslationsScheme,
    sizeScheme: SizeScheme
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(7) { dayOfWeekIndex ->
            Text(
                modifier = Modifier.weight(1f),
                text = translationsScheme.daysOfWeek3[dayOfWeekIndex],
                fontFamily = MONTSERRAT,
                fontSize = sizeScheme.font.yvHeaderWeek,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = NO_PADDING_TEXT_STYLE
            )
        }
    }
}

@Composable
fun WeekInYearCalendarGrid(
    modifier: Modifier = Modifier,
    weekIndex: Int,
    monthInfo: MonthInfo,
    colorScheme: ColorScheme,
    sizeScheme: SizeScheme
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(7) { dayOfWeekIndex ->
            DayInYearCalendarGrid(
                modifier = Modifier.weight(1f),
                weekIndex = weekIndex,
                dayOfWeekIndex = dayOfWeekIndex,
                monthInfo = monthInfo,
                colorScheme = colorScheme,
                sizeScheme = sizeScheme
            )
        }
    }
}

@Composable
fun DayInYearCalendarGrid(
    modifier: Modifier = Modifier,
    weekIndex: Int,
    dayOfWeekIndex: Int,
    monthInfo: MonthInfo,
    colorScheme: ColorScheme,
    sizeScheme: SizeScheme
) {
    val dayValue = getDayValueForMonthTableElement(
        weekIndex,
        dayOfWeekIndex,
        monthInfo.numberOfDays,
        monthInfo.dayOfWeekOf1st
    )
    val today = dayValue !== null && dayValue === monthInfo.today
    val holiday = isHoliday(dayValue, dayOfWeekIndex, monthInfo.holidays, monthInfo.notHolidays)
    Text(
        modifier = modifier
            .fillMaxSize()
            .drawBehind { if (today) drawCircle(
                color = Color.White,
                style = Stroke(width = 2f),
                center = this.center.plus(Offset(x = -1f, y = 0f))
            )}
            .wrapContentHeight(),
        text = (dayValue ?: "").toString(),
        fontFamily = MONTSERRAT_BOLD,
        fontSize = sizeScheme.font.yvDay,
        color = if (holiday) colorScheme.yvVeryLight else Color.White,
        textAlign = TextAlign.Center,
        style = NO_PADDING_TEXT_STYLE
    )
}
