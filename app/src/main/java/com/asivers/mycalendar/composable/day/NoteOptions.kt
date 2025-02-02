package com.asivers.mycalendar.composable.day

import android.content.Context
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.proto.addNote
import com.asivers.mycalendar.utils.proto.editNote
import com.asivers.mycalendar.utils.proto.removeNote

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
            label = "Notify",
            schemes = schemes
        )
        ActionNoteButton(
            modifier = Modifier.weight(1f),
            onClick = {
                getOnClickActionBtn(
                    ctx = ctx,
                    mutableNotes = mutableNotes,
                    inputMsg = inputMsg,
                    noteId = noteId,
                    noteMode = noteMode,
                    selectedDateInfo = selectedDateInfo
                )
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

private fun getOnClickActionBtn(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    inputMsg: MutableState<String>,
    noteId: MutableIntState,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo
) {
    when (noteMode.value) {
        NoteMode.OVERVIEW -> throw IllegalStateException()
        NoteMode.VIEW -> {
            removeNote(
                ctx = ctx,
                selectedDateInfo = selectedDateInfo,
                id = noteId.intValue
            )
            mutableNotes.removeIf { it.id == noteId.intValue }
        }
        NoteMode.ADD -> {
            val newNoteInfo = addNote(
                ctx = ctx,
                selectedDateInfo = selectedDateInfo,
                msg = inputMsg.value,
                isEveryYear = false,
                isHoliday = false
            )
            mutableNotes.add(0, newNoteInfo)
        }
        NoteMode.EDIT -> {
            val editedNoteInfo = editNote(
                ctx = ctx,
                selectedDateInfo = selectedDateInfo,
                id = noteId.intValue,
                msg = inputMsg.value,
                isEveryYear = false,
                isHoliday = false
            )
            for ((index, noteInfo) in mutableNotes.withIndex()) {
                if (noteInfo.id == noteId.intValue) {
                    mutableNotes.removeAt(index)
                    mutableNotes.add(index, editedNoteInfo)
                    break
                }
            }
        }
    }
    inputMsg.value = ""
    noteId.intValue = -1
    noteMode.value = NoteMode.OVERVIEW
}
