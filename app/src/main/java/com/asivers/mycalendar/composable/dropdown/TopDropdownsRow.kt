package com.asivers.mycalendar.composable.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo

@Composable
fun TopDropdownsRow(
    modifier: Modifier = Modifier,
    onYearSelected: (Int) -> Unit,
    onMonthSelected: (Int) -> Unit,
    selectedDateInfo: SelectedDateInfo,
    forYearView: Boolean,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(18.dp, 0.dp, 16.dp, 0.dp),
    ) {
        if (forYearView) {
            Image(
                modifier = Modifier.padding(4.dp, 0.dp),
                painter = painterResource(id = R.drawable.year_rat),
                contentDescription = "Symbol of the year icon"
            )
        } else {
            SelectMonthDropdown(
                modifier = Modifier.wrapContentWidth(),
                onMonthSelected = onMonthSelected,
                thisMonthValue = selectedDateInfo.monthValue,
                schemes = schemes
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SelectYearDropdown(
            modifier = Modifier.wrapContentWidth(),
            onYearSelected = onYearSelected,
            thisYear = selectedDateInfo.year,
            schemes = schemes
        )
    }
}
