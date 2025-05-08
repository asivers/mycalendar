package com.asivers.mycalendar.composable.day

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.month.BottomViewButton
import com.asivers.mycalendar.composable.month.ClickableSpacers
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.MutableNoteInfo
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.getNextNoteId
import com.asivers.mycalendar.utils.getNoteButtonGradient
import com.asivers.mycalendar.utils.getOnBackFromOneNoteMode
import com.asivers.mycalendar.utils.onHideKeyboard
import com.asivers.mycalendar.utils.proto.getNotes

@Composable
fun NotesSection(
    modifier: Modifier = Modifier,
    onSwipeToLeft: () -> Unit,
    onSwipeToRight: () -> Unit,
    refreshDaysLine: () -> Unit,
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
            refreshDaysLine = refreshDaysLine,
            selectedDateInfo = selectedDateInfo,
            holidayInfo = holidayInfo,
            schemes = schemes
        )
        NoteMode.VIEW, NoteMode.ADD, NoteMode.EDIT -> NotesSectionOneNoteMode(
            modifier = modifier,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            refreshDaysLine = refreshDaysLine,
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
    refreshDaysLine: () -> Unit,
    selectedDateInfo: SelectedDateInfo,
    holidayInfo: String?,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            contentAlignment = Alignment.Center
        ) {
            if (holidayInfo != null) {
                Text(
                    text = holidayInfo,
                    fontFamily = MONTSERRAT_MEDIUM,
                    fontSize = schemes.size.font.yvMonthName,
                    color = schemes.color.text
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ExistingNotes(
                modifier = Modifier.padding(8.dp, 0.dp),
                mutableNotes = mutableNotes,
                onClickToNote = {
                    mutableNoteInfo.value = MutableNoteInfo(it)
                    noteMode.value = NoteMode.VIEW
                },
                refreshDaysLine = refreshDaysLine,
                selectedDateInfo = selectedDateInfo,
                schemes = schemes
            )
            ClickableSpacers(
                onClickLeft = onSwipeToRight,
                onClickRight = onSwipeToLeft
            )
        }
        BottomViewButton(
            onClick = {
                val nextId = getNextNoteId(ctx)
                mutableNoteInfo.value.id = nextId
                noteMode.value = NoteMode.ADD
            },
            text = schemes.translation.makeNote,
            background = getNoteButtonGradient(schemes.color),
            schemes = schemes,
            textColor = schemes.color.viewsBottom
        )
    }
}

@Composable
fun NotesSectionOneNoteMode(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    noteMode: MutableState<NoteMode>,
    refreshDaysLine: () -> Unit,
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
            refreshDaysLine = refreshDaysLine,
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
            modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 16.dp),
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            refreshDaysLine = refreshDaysLine,
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
