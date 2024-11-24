package com.asivers.mycalendar.composable.year

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.HolidaysForCountry
import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.data.scheme.size.SizeScheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getHolidaysForCountryForPreview
import com.asivers.mycalendar.utils.getSizeScheme
import com.asivers.mycalendar.utils.getYearViewBackgroundGradient

@Preview(showBackground = true)
@Composable
fun YearViewPreview() {
    YearView(
        selectedYear = remember { mutableIntStateOf(getCurrentYear()) },
        selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
        showYearView = remember { mutableStateOf(true) },
        lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) },
        holidaysForCountry = getHolidaysForCountryForPreview(),
        colorScheme = SUMMER,
        sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun YearView(
    modifier: Modifier = Modifier,
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    showYearView: MutableState<Boolean>,
    lastSelectedYearFromMonthView: MutableIntState,
    holidaysForCountry: HolidaysForCountry,
    colorScheme: ColorScheme,
    sizeScheme: SizeScheme
) {
    var horizontalOffset by remember { mutableFloatStateOf(0f) }
    var verticalOffset by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(36.dp, 36.dp))
            .background(getYearViewBackgroundGradient(colorScheme))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        horizontalOffset = 0f
                        verticalOffset = 0f
                    },
                    onDragEnd = {
                        if (verticalOffset > 100f) {
                            selectedYear.intValue = lastSelectedYearFromMonthView.intValue
                            showYearView.value = false
                        } else if (horizontalOffset > 50f && selectedYear.intValue > 1900) {
                            selectedYear.intValue--
                        } else if (horizontalOffset < -50f && selectedYear.intValue < 2100) {
                            selectedYear.intValue++
                        }
                    }
                ) { _, dragAmount ->
                    horizontalOffset += dragAmount.x
                    verticalOffset += dragAmount.y
                }
            }
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        TopDropdownsRow(
            selectedYear = selectedYear,
            selectedMonthIndex = selectedMonthIndex,
            showYearView = showYearView.value,
            lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
            colorScheme = colorScheme,
            sizeScheme = sizeScheme
        )
        Box(
            modifier = Modifier.weight(1f)
        ) {
            YearCalendarGrid(
                thisYear = selectedYear.intValue,
                selectedMonthIndex = selectedMonthIndex,
                showYearView = showYearView,
                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                holidaysForCountry = holidaysForCountry,
                colorScheme = colorScheme,
                sizeScheme = sizeScheme
            )
        }
    }
}
