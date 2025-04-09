package com.asivers.mycalendar.utils

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.asivers.mycalendar.data.MutableNoteInfo
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.proto.addNote
import com.asivers.mycalendar.utils.proto.editNote
import com.asivers.mycalendar.utils.proto.removeNote

fun getOnBackFromOneNoteMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    noteMode: MutableState<NoteMode>,
    refreshDaysLine: () -> Unit,
    selectedDateInfo: SelectedDateInfo
) {
    when (noteMode.value) {
        NoteMode.OVERVIEW -> throw IllegalStateException()
        NoteMode.VIEW -> getOnBackFromViewMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            selectedDateInfo = selectedDateInfo
        )
        NoteMode.ADD -> getOnCompleteAddMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            refreshDaysLine = refreshDaysLine,
            selectedDateInfo = selectedDateInfo
        )
        NoteMode.EDIT -> getOnCompleteEditMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            refreshDaysLine = refreshDaysLine,
            selectedDateInfo = selectedDateInfo
        )
    }
}

fun getOnCompleteOneNoteMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    noteMode: MutableState<NoteMode>,
    refreshDaysLine: () -> Unit,
    selectedDateInfo: SelectedDateInfo
) {
    when (noteMode.value) {
        NoteMode.OVERVIEW -> throw IllegalStateException()
        NoteMode.VIEW -> getOnDeleteNoteInViewMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            refreshDaysLine = refreshDaysLine,
            selectedDateInfo = selectedDateInfo
        )
        NoteMode.ADD -> getOnCompleteAddMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            refreshDaysLine = refreshDaysLine,
            selectedDateInfo = selectedDateInfo
        )
        NoteMode.EDIT -> getOnCompleteEditMode(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            noteMode = noteMode,
            refreshDaysLine = refreshDaysLine,
            selectedDateInfo = selectedDateInfo
        )
    }
}

private fun getOnBackFromViewMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    noteMode: MutableState<NoteMode>,
    selectedDateInfo: SelectedDateInfo
) {
    if (mutableNoteInfo.value.changed) {
        editNoteAndUpdateStates(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            selectedDateInfo = selectedDateInfo
        )
    }
    mutableNoteInfo.value = MutableNoteInfo()
    noteMode.value = NoteMode.OVERVIEW
}

private fun getOnDeleteNoteInViewMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    noteMode: MutableState<NoteMode>,
    refreshDaysLine: () -> Unit,
    selectedDateInfo: SelectedDateInfo
) {
    removeNoteAndUpdateStates(
        ctx = ctx,
        mutableNotes = mutableNotes,
        mutableNoteInfo = mutableNoteInfo,
        selectedDateInfo = selectedDateInfo
    )
    noteMode.value = NoteMode.OVERVIEW
    refreshDaysLine()
}

private fun getOnCompleteAddMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    noteMode: MutableState<NoteMode>,
    refreshDaysLine: () -> Unit,
    selectedDateInfo: SelectedDateInfo
) {
    if (mutableNoteInfo.value.msg.isBlank()) {
        mutableNoteInfo.value = MutableNoteInfo()
        noteMode.value = NoteMode.OVERVIEW
    } else {
        addNoteAndUpdateStates(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            selectedDateInfo = selectedDateInfo
        )
        noteMode.value = NoteMode.VIEW
        refreshDaysLine()
    }
}

private fun getOnCompleteEditMode(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    noteMode: MutableState<NoteMode>,
    refreshDaysLine: () -> Unit,
    selectedDateInfo: SelectedDateInfo
) {
    if (mutableNoteInfo.value.msg.isBlank()) {
        removeNoteAndUpdateStates(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            selectedDateInfo = selectedDateInfo
        )
        noteMode.value = NoteMode.OVERVIEW
        refreshDaysLine()
    } else {
        editNoteAndUpdateStates(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            selectedDateInfo = selectedDateInfo
        )
        noteMode.value = NoteMode.VIEW
    }
}

private fun addNoteAndUpdateStates(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    selectedDateInfo: SelectedDateInfo
) {
    val noteId = mutableNoteInfo.value.id
    val noteMsg = mutableNoteInfo.value.msg
    val isEveryYear = mutableNoteInfo.value.isEveryYear
    val notificationTime = mutableNoteInfo.value.notificationTime
    val newNoteInfo = addNote(
        ctx = ctx,
        selectedDateInfo = selectedDateInfo,
        id = noteId,
        msg = noteMsg,
        isEveryYear = isEveryYear,
        notificationTime = notificationTime
    )
    mutableNotes.add(0, newNoteInfo)
    mutableNoteInfo.value.id = newNoteInfo.id
    mutableNoteInfo.value.changed = false
}

private fun removeNoteAndUpdateStates(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    selectedDateInfo: SelectedDateInfo
) {
    val noteId = mutableNoteInfo.value.id
    removeNote(
        ctx = ctx,
        selectedDateInfo = selectedDateInfo,
        id = noteId
    )
    mutableNotes.removeIf { it.id == noteId }
    mutableNoteInfo.value = MutableNoteInfo()
}

private fun editNoteAndUpdateStates(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    selectedDateInfo: SelectedDateInfo
) {
    val noteId = mutableNoteInfo.value.id
    val noteMsg = mutableNoteInfo.value.msg
    val isEveryYear = mutableNoteInfo.value.isEveryYear
    val notificationTime = mutableNoteInfo.value.notificationTime
    val editedNoteInfo = editNote(
        ctx = ctx,
        selectedDateInfo = selectedDateInfo,
        id = noteId,
        msg = noteMsg,
        isEveryYear = isEveryYear,
        notificationTime = notificationTime
    )
    for ((index, noteInfo) in mutableNotes.withIndex()) {
        if (noteInfo.id == noteId) {
            mutableNotes.removeAt(index)
            mutableNotes.add(index, editedNoteInfo)
            break
        }
    }
    mutableNoteInfo.value.changed = false
}
