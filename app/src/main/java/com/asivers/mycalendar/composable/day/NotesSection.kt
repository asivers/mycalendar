package com.asivers.mycalendar.composable.day

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.noRippleClickable
import com.asivers.mycalendar.utils.proto.getNotes

@Composable
fun NotesSection(
    modifier: Modifier = Modifier,
    selectedDateInfo: SelectedDateInfo,
    holidayInfo: String?,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val mutableNotes = remember(selectedDateInfo) {
        getNotes(ctx, selectedDateInfo).reversed().toMutableStateList()
    }
    val inputMsg = remember(selectedDateInfo) { mutableStateOf("") }
    val noteId = remember(selectedDateInfo) { mutableIntStateOf(-1) }
    val noteMode = remember(selectedDateInfo) { mutableStateOf(NoteMode.OVERVIEW) }

    when (noteMode.value) {
        NoteMode.OVERVIEW -> NotesSectionOverviewMode(
            modifier = modifier,
            mutableNotes = mutableNotes,
            inputMsg = inputMsg,
            noteId = noteId,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo,
            holidayInfo = holidayInfo,
            schemes = schemes
        )
        NoteMode.VIEW, NoteMode.ADD, NoteMode.EDIT -> NotesSectionOneNoteMode(
            modifier = modifier,
            mutableNotes = mutableNotes,
            inputMsg = inputMsg,
            noteId = noteId,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
    }
}

@Composable
fun NotesSectionOverviewMode(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    inputMsg: MutableState<String>,
    noteId: MutableIntState,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo,
    holidayInfo: String?,
    schemes: SchemeContainer
) {
    Column(modifier = modifier.padding(8.dp)) {
        if (holidayInfo != null) {
            Text(
                text = holidayInfo,
                modifier = Modifier.padding(16.dp, 20.dp),
                fontFamily = MONTSERRAT_MEDIUM,
                fontSize = schemes.size.font.dropdownItem,
                color = schemes.color.text
            )
        }
        ExistingNotes(
            mutableNotes = mutableNotes,
            onClickToNote = {
                inputMsg.value = it.msg
                noteId.intValue = it.id
                noteMode.value = NoteMode.VIEW
            },
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
        Box(
            modifier = modifier
                .alpha(0.3f)
                .noRippleClickable { noteMode.value = NoteMode.ADD },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Enter new note",
                fontFamily = MONTSERRAT_MEDIUM,
                fontSize = schemes.size.font.dropdownItem,
                color = schemes.color.text
            )
        }
    }
}

@Composable
fun NotesSectionOneNoteMode(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    inputMsg: MutableState<String>,
    noteId: MutableIntState,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    BackHandler {
        inputMsg.value = ""
        noteId.intValue = -1
        noteMode.value = if (noteMode.value == NoteMode.EDIT) NoteMode.VIEW else NoteMode.OVERVIEW
    }
    Column(modifier = modifier) {
        NoteOptions(
            modifier = Modifier.padding(8.dp),
            mutableNotes = mutableNotes,
            inputMsg = inputMsg,
            noteId = noteId,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
        InputNote(
            modifier = Modifier.weight(1f),
            onValueChange = { inputMsg.value = it },
            onClick = { noteMode.value = NoteMode.EDIT },
            inputMsg = inputMsg.value,
            noteMode = noteMode.value,
            schemes = schemes
        )
    }
}
