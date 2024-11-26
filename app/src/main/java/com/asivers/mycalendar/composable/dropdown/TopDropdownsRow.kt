package com.asivers.mycalendar.composable.dropdown

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.data.scheme.size.SizeScheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getSizeScheme

@Preview
@Composable
fun TopDropdownsRowPreview() {
    TopDropdownsRow(
        selectedYear = remember { mutableIntStateOf(getCurrentYear()) },
        selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
        showYearView = false,
        lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) },
        colorScheme = SUMMER,
        sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun TopDropdownsRow(
    modifier: Modifier = Modifier,
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    showYearView: Boolean,
    lastSelectedYearFromMonthView: MutableIntState,
    colorScheme: ColorScheme,
    sizeScheme: SizeScheme
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(18.dp, 8.dp, 16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showYearView) {
            Text(
                text = stringResource(id = R.string.year_view),
                fontFamily = MONTSERRAT_BOLD,
                fontSize = sizeScheme.font.main,
                color = Color.White,
            )
        } else {
            SelectMonthDropdown(
                modifier = Modifier.wrapContentWidth(),
                selectedMonthIndex = selectedMonthIndex,
                colorScheme = colorScheme,
                sizeScheme = sizeScheme
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SelectYearDropdown(
            modifier = Modifier.wrapContentWidth(),
            selectedYear = selectedYear,
            showYearView = showYearView,
            lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
            colorScheme = colorScheme,
            sizeScheme = sizeScheme
        )
    }
}
