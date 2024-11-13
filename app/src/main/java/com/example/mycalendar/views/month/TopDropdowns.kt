package com.example.mycalendar.views.month

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        SelectMonthDropdown(
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        SelectYearDropdown(
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}
