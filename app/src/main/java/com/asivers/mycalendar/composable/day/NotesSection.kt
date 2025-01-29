package com.asivers.mycalendar.composable.day

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.asivers.mycalendar.utils.noRippleClickable
import com.asivers.mycalendar.utils.proto.getNotes

@Composable
fun NotesSection(
    modifier: Modifier = Modifier,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val mutableNotes = remember(selectedDateInfo) {
        getNotes(ctx, selectedDateInfo).toMutableStateList()
    }
    val msg = remember { mutableStateOf("") }
    val editModeEnabled = remember { mutableStateOf(false) }

    if (editModeEnabled.value) {
        NotesSectionEditMode(
            modifier = modifier,
            mutableNotes = mutableNotes,
            msg = msg,
            editModeEnabled = editModeEnabled,
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
    } else {
        NotesSectionViewMode(
            modifier = modifier,
            mutableNotes = mutableNotes,
            editModeEnabled = editModeEnabled,
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
    }
}

@Composable
fun NotesSectionViewMode(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    editModeEnabled: MutableState<Boolean>,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    Column(modifier = modifier.padding(8.dp)) {
        ExistingNotes(
            mutableNotes = mutableNotes,
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
        Box(
            modifier = modifier
                .weight(1f)
                .fillMaxSize()
                .alpha(0.3f)
                .noRippleClickable { editModeEnabled.value = true },
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
fun NotesSectionEditMode(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    msg: MutableState<String>,
    editModeEnabled: MutableState<Boolean>,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    BackHandler {
        editModeEnabled.value = false
    }
    Column(modifier = modifier.padding(8.dp)) {
        InputNote(
            modifier = Modifier.weight(1f),
            msg = msg,
            schemes = schemes
        )
        NoteOptions(
            schemes = schemes
        )
        Spacer(modifier = Modifier.height(12.dp))
        SaveNoteButton(
            mutableNotes = mutableNotes,
            msg = msg,
            onExitEditMode = {
                msg.value = ""
                editModeEnabled.value = false
            },
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
    }
}
