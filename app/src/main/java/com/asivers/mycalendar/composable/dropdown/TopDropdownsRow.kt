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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getSchemesForPreview

@Preview
@Composable
fun TopDropdownsRowPreview() {
    TopDropdownsRow(
        selectedYear = remember { mutableIntStateOf(getCurrentYear()) },
        selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) },
        showYearView = false,
        lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) },
        schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun TopDropdownsRow(
    modifier: Modifier = Modifier,
    selectedYear: MutableIntState,
    selectedMonthIndex: MutableIntState,
    showYearView: Boolean,
    lastSelectedYearFromMonthView: MutableIntState,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(18.dp, 8.dp, 16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showYearView) {
            Text(
                text = schemes.translation.yearView,
                fontFamily = MONTSERRAT_BOLD,
                fontSize = schemes.size.font.main,
                color = Color.White,
            )
        } else {
            SelectMonthDropdown(
                modifier = Modifier.wrapContentWidth(),
                selectedMonthIndex = selectedMonthIndex,
                schemes = schemes
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SelectYearDropdown(
            modifier = Modifier.wrapContentWidth(),
            selectedYear = selectedYear,
            showYearView = showYearView,
            lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
            schemes = schemes
        )
    }
}
