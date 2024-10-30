package com.example.mycalendar.views.month

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TopDropdowns() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        SelectMonthDropdown()
        SelectYearDropdown()
    }
}

@Composable
fun SelectMonthDropdown() {
    DropdownMenu(
        expanded = false,
        onDismissRequest = { /*TODO*/ }
    ) {

    }
}

@Composable
fun SelectYearDropdown() {
    DropdownMenu(
        expanded = false,
        onDismissRequest = { /*TODO*/ }
    ) {

    }
}
