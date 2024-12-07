package com.asivers.mycalendar.composable.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getSchemesForPreview

@Preview
@Composable
fun TopDropdownsRowPreview() {
    TopDropdownsRow(
        selectedYearInfo = remember { mutableStateOf(SelectedYearInfo(getCurrentYear())) },
        selectedMonthInfo = remember { mutableStateOf(SelectedMonthInfo(getCurrentYear(), getCurrentMonthIndex())) },
        showYearView = false,
        schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun TopDropdownsRow(
    modifier: Modifier = Modifier,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    showYearView: Boolean,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(18.dp, 0.dp, 16.dp, 0.dp),
    ) {
        if (showYearView) {
            Image(
                modifier = Modifier.padding(4.dp, 0.dp),
                painter = painterResource(id = R.drawable.year_rat),
                contentDescription = "Symbol of the year icon"
            )
        } else {
            SelectMonthDropdown(
                modifier = Modifier.wrapContentWidth(),
                selectedMonthInfo = selectedMonthInfo,
                schemes = schemes
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SelectYearDropdown(
            modifier = Modifier.wrapContentWidth(),
            selectedYearInfo = selectedYearInfo,
            selectedMonthInfo = selectedMonthInfo.value,
            showYearView = showYearView,
            schemes = schemes
        )
    }
}
