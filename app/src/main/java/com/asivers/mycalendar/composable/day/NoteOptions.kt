package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer

@Composable
fun NoteOptions(
    modifier: Modifier = Modifier,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        val everyYearSwitchState = remember { mutableStateOf(false) }
        SwitchWithLabel(
            modifier = Modifier.weight(1f),
            checked = everyYearSwitchState.value,
            onCheckedChange = { everyYearSwitchState.value = it },
            label = "Every year", // todo translations
            schemes = schemes
        )
        val holidaySwitchState = remember { mutableStateOf(false) }
        SwitchWithLabel(
            modifier = Modifier.weight(1f),
            checked = holidaySwitchState.value,
            onCheckedChange = { holidaySwitchState.value = it },
            label = "Holiday",
            schemes = schemes
        )
        val notificationSwitchState = remember { mutableStateOf(false) }
        SwitchWithLabel(
            modifier = Modifier.weight(1f),
            checked = notificationSwitchState.value,
            onCheckedChange = { notificationSwitchState.value = it },
            label = "Notification",
            schemes = schemes
        )
    }
}

@Composable
fun SwitchWithLabel(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    schemes: SchemeContainer
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = schemes.color.monthViewTop
            )
        )
        Text(
            text = label,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.yvMonthName,
            color = schemes.color.text
        )
    }
}
