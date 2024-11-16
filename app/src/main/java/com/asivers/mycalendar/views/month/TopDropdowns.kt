package com.asivers.mycalendar.views.month

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TopDropdownsPreview() {
    TopDropdowns()
}

@Composable
fun TopDropdowns() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SelectMonthDropdown(
            modifier = Modifier.wrapContentWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        SelectYearDropdown(
            modifier = Modifier.wrapContentWidth()
        )
    }
}
