package com.asivers.mycalendar.utils

import android.content.Context
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.proto.addNote
import com.asivers.mycalendar.utils.proto.editNote
import com.asivers.mycalendar.utils.proto.removeNote

fun getOnBackFromOneNoteMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    inputMsg: MutableState<String>,
    noteId: MutableIntState,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo
) {
    when (noteMode.value) {
        NoteMode.OVERVIEW -> throw IllegalStateException()
        NoteMode.VIEW -> getOnBackFromViewMode(
            inputMsg = inputMsg,
            noteId = noteId,
            noteMode = noteMode
        )
        NoteMode.ADD -> getOnCompleteAddMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            inputMsg = inputMsg,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo
        )
        NoteMode.EDIT -> getOnCompleteEditMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            inputMsg = inputMsg,
            noteId = noteId,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo
        )
    }
}

fun getOnCompleteOneNoteMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    inputMsg: MutableState<String>,
    noteId: MutableIntState,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo
) {
    when (noteMode.value) {
        NoteMode.OVERVIEW -> throw IllegalStateException()
        NoteMode.VIEW -> getOnDeleteNoteInViewMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            inputMsg = inputMsg,
            noteId = noteId,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo
        )
        NoteMode.ADD -> getOnCompleteAddMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            inputMsg = inputMsg,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo
        )
        NoteMode.EDIT -> getOnCompleteEditMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            inputMsg = inputMsg,
            noteId = noteId,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo
        )
    }
}

private fun getOnBackFromViewMode(
    inputMsg: MutableState<String>,
    noteId: MutableIntState,
    noteMode: MutableState<NoteMode>
) {
    inputMsg.value = ""
    noteId.intValue = -1
    noteMode.value = NoteMode.OVERVIEW
}

private fun getOnDeleteNoteInViewMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    inputMsg: MutableState<String>,
    noteId: MutableIntState,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo
) {
    removeNote(
        ctx = ctx,
        selectedDateInfo = selectedDateInfo,
        id = noteId.intValue
    )
    mutableNotes.removeIf { it.id == noteId.intValue }
    inputMsg.value = ""
    noteId.intValue = -1
    noteMode.value = NoteMode.OVERVIEW
}

private fun getOnCompleteAddMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    inputMsg: MutableState<String>,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo
) {
    if (inputMsg.value.isBlank()) {
        noteMode.value = NoteMode.OVERVIEW
    } else {
        val newNoteInfo = addNote(
            ctx = ctx,
            selectedDateInfo = selectedDateInfo,
            msg = inputMsg.value,
            isEveryYear = false,
            isHoliday = false
        )
        mutableNotes.add(0, newNoteInfo)
        noteMode.value = NoteMode.VIEW
    }
}

private fun getOnCompleteEditMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    inputMsg: MutableState<String>,
    noteId: MutableIntState,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo
) {
    if (inputMsg.value.isBlank()) {
        removeNote(
            ctx = ctx,
            selectedDateInfo = selectedDateInfo,
            id = noteId.intValue
        )
        mutableNotes.removeIf { it.id == noteId.intValue }
        noteMode.value = NoteMode.OVERVIEW
    } else {
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
        noteMode.value = NoteMode.VIEW
    }
}
