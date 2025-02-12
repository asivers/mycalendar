package com.asivers.mycalendar.composable.day

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.MutableNoteInfo
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.getOnBackFromOneNoteMode
import com.asivers.mycalendar.utils.noRippleClickable
import com.asivers.mycalendar.utils.onHideKeyboard
import com.asivers.mycalendar.utils.onHorizontalSwipe
import com.asivers.mycalendar.utils.proto.getNotes

@Composable
fun NotesSection(
    modifier: Modifier = Modifier,
    onSwipeToLeft: () -> Unit,
    onSwipeToRight: () -> Unit,
    selectedDateInfo: SelectedDateInfo,
    holidayInfo: String?,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val mutableNotes = remember(selectedDateInfo) {
        getNotes(ctx, selectedDateInfo).reversed().toMutableStateList()
    }
    val mutableNoteInfo = remember(selectedDateInfo) { mutableStateOf(MutableNoteInfo()) }
    val noteMode = remember(selectedDateInfo) { mutableStateOf(NoteMode.OVERVIEW) }

    when (noteMode.value) {
        NoteMode.OVERVIEW -> NotesSectionOverviewMode(
            modifier = modifier,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            onSwipeToLeft = onSwipeToLeft,
            onSwipeToRight = onSwipeToRight,
            selectedDateInfo = selectedDateInfo,
            holidayInfo = holidayInfo,
            schemes = schemes
        )
        NoteMode.VIEW, NoteMode.ADD, NoteMode.EDIT -> NotesSectionOneNoteMode(
            modifier = modifier,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
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
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    noteMode: MutableState<NoteMode>,
    onSwipeToLeft: () -> Unit,
    onSwipeToRight: () -> Unit,
    selectedDateInfo: SelectedDateInfo,
    holidayInfo: String?,
    schemes: SchemeContainer
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (holidayInfo != null) {
            Text(
                text = holidayInfo,
                fontFamily = MONTSERRAT_MEDIUM,
                fontSize = schemes.size.font.dropdownItem,
                color = schemes.color.text
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        ExistingNotes(
            mutableNotes = mutableNotes,
            onClickToNote = {
                mutableNoteInfo.value = MutableNoteInfo(it)
                noteMode.value = NoteMode.VIEW
            },
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
        val horizontalOffset = remember { mutableFloatStateOf(0f) }
        Box(
            modifier = modifier
                .alpha(0.5f)
                .noRippleClickable { noteMode.value = NoteMode.ADD }
                .onHorizontalSwipe(horizontalOffset, onSwipeToLeft, onSwipeToRight),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = schemes.translation.newNote,
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
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val localFocusManager = LocalFocusManager.current
    val onBackFromOneNoteMode = {
        getOnBackFromOneNoteMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo
        )
        localFocusManager.clearFocus()
    }
    BackHandler {
        onBackFromOneNoteMode()
    }
    Column(
        modifier = modifier.onHideKeyboard { onBackFromOneNoteMode() }
    ) {
        NoteOptions(
            modifier = Modifier.padding(8.dp),
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
        InputNote(
            modifier = Modifier.weight(1f),
            onValueChange = { updateCreateNoteInfoMsg(mutableNoteInfo, it) },
            onClick = { noteMode.value = NoteMode.EDIT },
            initialMsg = mutableNoteInfo.value.msg,
            noteMode = noteMode.value,
            schemes = schemes
        )
    }
}

private fun updateCreateNoteInfoMsg(
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    newMsg: String
) {
    if (newMsg.isBlank() || mutableNoteInfo.value.msg.isBlank()) {
        mutableNoteInfo.value = mutableNoteInfo.value.refreshMsg(newMsg)
    } else {
        mutableNoteInfo.value.msg = newMsg
        mutableNoteInfo.value.changed = true
    }
}
