package com.asivers.mycalendar.views.month

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asivers.mycalendar.ui.theme.custom.CustomColorScheme
import com.asivers.mycalendar.ui.theme.custom.CustomFont
import com.asivers.mycalendar.ui.theme.custom.summerColorScheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear

@Preview
@Composable
fun TopDropdownsRowPreview() {
    TopDropdownsRow(
        selectedYear = remember { mutableIntStateOf(getCurrentYear()) },
        selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
        showYearView = false,
        lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) },
        colorScheme = summerColorScheme
    )
}

@Composable
fun TopDropdownsRow(
    modifier: Modifier = Modifier,
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    showYearView: Boolean,
    lastSelectedYearFromMonthView: MutableIntState,
    colorScheme: CustomColorScheme
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(18.dp, 8.dp, 16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showYearView) {
            Text(
                text = "Year view",
                fontFamily = CustomFont.MONTSERRAT_BOLD,
                fontSize = 24.sp,
                color = Color.White,
            )
        } else {
            SelectMonthDropdown(
                modifier = Modifier.wrapContentWidth(),
                selectedMonthIndex = selectedMonthIndex,
                colorScheme = colorScheme
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SelectYearDropdown(
            modifier = Modifier.wrapContentWidth(),
            selectedYear = selectedYear,
            showYearView = showYearView,
            lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
            colorScheme = colorScheme
        )
    }
}
