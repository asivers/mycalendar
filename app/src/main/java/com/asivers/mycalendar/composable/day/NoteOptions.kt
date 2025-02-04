package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.getOnCompleteOneNoteMode

@Composable
fun NoteOptions(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    inputMsg: MutableState<String>,
    noteId: MutableIntState,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val localFocusManager = LocalFocusManager.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        val everyYearSwitchState = remember { mutableStateOf(false) }
        SwitchWithLabel(
            modifier = Modifier.weight(1f),
            checked = everyYearSwitchState.value,
            onCheckedChange = { everyYearSwitchState.value = it },
            label = schemes.translation.switchEveryYear,
            schemes = schemes
        )
        val holidaySwitchState = remember { mutableStateOf(false) }
        SwitchWithLabel(
            modifier = Modifier.weight(1f),
            checked = holidaySwitchState.value,
            onCheckedChange = { holidaySwitchState.value = it },
            label = schemes.translation.switchHoliday,
            schemes = schemes
        )
        val notificationSwitchState = remember { mutableStateOf(false) }
        SwitchWithLabel(
            modifier = Modifier.weight(1f),
            checked = notificationSwitchState.value,
            onCheckedChange = { notificationSwitchState.value = it },
            label = schemes.translation.switchNotify,
            schemes = schemes
        )
        ActionNoteButton(
            modifier = Modifier.weight(1f),
            onClick = {
                getOnCompleteOneNoteMode(
                    ctx = ctx,
                    mutableNotes = mutableNotes,
                    inputMsg = inputMsg,
                    noteId = noteId,
                    noteMode = noteMode,
                    selectedDateInfo = selectedDateInfo
                )
                localFocusManager.clearFocus()
            },
            noteMode = noteMode.value,
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
            color = schemes.color.text,
            textAlign = TextAlign.Center
        )
    }
}
