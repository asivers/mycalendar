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
        editNoteAndUpdateAlarmAndUpdateStates(
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
    removeNoteAndCancelAlarmAndUpdateStates(
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
        addNoteAndSetAlarmAndUpdateStates(
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
        removeNoteAndCancelAlarmAndUpdateStates(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            selectedDateInfo = selectedDateInfo
        )
        noteMode.value = NoteMode.OVERVIEW
        refreshDaysLine()
    } else {
        editNoteAndUpdateAlarmAndUpdateStates(
            ctx = ctx,
            mutableNotes = mutableNotes,
            mutableNoteInfo = mutableNoteInfo,
            selectedDateInfo = selectedDateInfo
        )
        noteMode.value = NoteMode.VIEW
    }
}

private fun addNoteAndSetAlarmAndUpdateStates(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    selectedDateInfo: SelectedDateInfo
) {
    val noteId = mutableNoteInfo.value.id
    val noteMsg = mutableNoteInfo.value.msg
    val isEveryYear = mutableNoteInfo.value.isEveryYear
    val notificationTime = mutableNoteInfo.value.notificationTime

    if (notificationTime != null) {
        val alarmSetSuccessfully = setExactAlarm(
            ctx = ctx,
            selectedDateInfo = selectedDateInfo,
            noteId = noteId,
            alarmMessage = noteMsg,
            isEveryYear = isEveryYear,
            notificationTime = notificationTime
        )
        if (!alarmSetSuccessfully) {
            mutableNoteInfo.value.notificationTime = null
        }
    }

    val newNoteInfo = addNote(
        ctx = ctx,
        selectedDateInfo = selectedDateInfo,
        id = noteId,
        msg = noteMsg,
        isEveryYear = isEveryYear,
        notificationTime = mutableNoteInfo.value.notificationTime
    )
    mutableNotes.add(0, newNoteInfo)
    mutableNoteInfo.value.changed = false
}

private fun removeNoteAndCancelAlarmAndUpdateStates(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    selectedDateInfo: SelectedDateInfo
) {
    val noteId = mutableNoteInfo.value.id
    cancelExactAlarmIfExists(
        ctx = ctx,
        noteId = noteId
    )
    removeNote(
        ctx = ctx,
        selectedDateInfo = selectedDateInfo,
        id = noteId
    )
    mutableNotes.removeIf { it.id == noteId }
    mutableNoteInfo.value = MutableNoteInfo()
}

private fun editNoteAndUpdateAlarmAndUpdateStates(
    ctx: Context,
    mutableNotes: SnapshotStateList<NoteInfo>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    selectedDateInfo: SelectedDateInfo
) {
    val noteId = mutableNoteInfo.value.id
    val noteMsg = mutableNoteInfo.value.msg
    val isEveryYear = mutableNoteInfo.value.isEveryYear
    val notificationTime = mutableNoteInfo.value.notificationTime

    cancelExactAlarmIfExists(
        ctx = ctx,
        noteId = noteId
    )
    if (notificationTime != null) {
        val alarmSetSuccessfully = setExactAlarm(
            ctx = ctx,
            selectedDateInfo = selectedDateInfo,
            noteId = noteId,
            alarmMessage = noteMsg,
            isEveryYear = isEveryYear,
            notificationTime = notificationTime
        )
        if (!alarmSetSuccessfully) {
            mutableNoteInfo.value.notificationTime = null
        }
    }

    val editedNoteInfo = editNote(
        ctx = ctx,
        selectedDateInfo = selectedDateInfo,
        id = noteId,
        msg = noteMsg,
        isEveryYear = isEveryYear,
        notificationTime = mutableNoteInfo.value.notificationTime
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
