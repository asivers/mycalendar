package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.data.MutableNoteInfo
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.date.isInFuture
import com.asivers.mycalendar.utils.getOnClickSwitchInOneNoteMode
import com.asivers.mycalendar.utils.getOnCompleteOneNoteMode
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun NoteOptions(
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
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        val messageIsNotBlank = mutableNoteInfo.value.msg.isNotBlank()
        SwitchWithLabel(
            modifier = Modifier
                .weight(3f)
                .alpha(if (messageIsNotBlank) 1f else 0.4f),
            checked = mutableNoteInfo.value.isEveryYear,
            onCheckedChange = {
                val notificationTime = mutableNoteInfo.value.notificationTime
                if (!it && notificationTime != null
                    && !isInFuture(selectedDateInfo, notificationTime)) {
                    mutableNoteInfo.value.notificationTime = null
                }
                mutableNoteInfo.value = mutableNoteInfo.value.refreshIsEveryYear(it)
                getOnClickSwitchInOneNoteMode(
                    ctx = ctx,
                    mutableNotes = mutableNotes,
                    mutableNoteInfo = mutableNoteInfo,
                    noteMode = noteMode,
                    refreshDaysLine = refreshDaysLine,
                    selectedDateInfo = selectedDateInfo
                )
                localFocusManager.clearFocus()
            },
            enabled = messageIsNotBlank,
            label = schemes.translation.switchEveryYear,
            schemes = schemes
        )
        val enabledNotificationSwitch = messageIsNotBlank
                && (mutableNoteInfo.value.isEveryYear || !isSelectedDateInPast(selectedDateInfo))
        val dialogOpened = remember { mutableStateOf(false) }
        SwitchWithLabel(
            modifier = Modifier
                .weight(3f)
                .alpha(if (enabledNotificationSwitch) 1f else 0.4f),
            checked = mutableNoteInfo.value.notificationTime != null,
            onCheckedChange = { switchedOn ->
                if (switchedOn) {
                    dialogOpened.value = true
                } else {
                    mutableNoteInfo.value = mutableNoteInfo.value.refreshNotificationTime(null)
                }
                getOnClickSwitchInOneNoteMode(
                    ctx = ctx,
                    mutableNotes = mutableNotes,
                    mutableNoteInfo = mutableNoteInfo,
                    noteMode = noteMode,
                    refreshDaysLine = refreshDaysLine,
                    selectedDateInfo = selectedDateInfo
                )
                localFocusManager.clearFocus()
            },
            enabled = enabledNotificationSwitch,
            label = mutableNoteInfo.value.notificationTime?.toString()
                ?: schemes.translation.switchNotification,
            schemes = schemes
        )
        ActionNoteButton(
            modifier = Modifier
                .weight(3f)
                .alpha(if (messageIsNotBlank) 1f else 0.4f),
            onClick = {
                if (messageIsNotBlank) {
                    getOnCompleteOneNoteMode(
                        ctx = ctx,
                        mutableNotes = mutableNotes,
                        mutableNoteInfo = mutableNoteInfo,
                        noteMode = noteMode,
                        refreshDaysLine = refreshDaysLine,
                        selectedDateInfo = selectedDateInfo
                    )
                    localFocusManager.clearFocus()
                }
            },
            noteMode = noteMode.value,
            schemes = schemes
        )
        SetNotificationDialogs(
            dialogOpened = dialogOpened,
            mutableNoteInfo = mutableNoteInfo,
            onNotificationTimeRefreshed = {
                getOnClickSwitchInOneNoteMode(
                    ctx = ctx,
                    mutableNotes = mutableNotes,
                    mutableNoteInfo = mutableNoteInfo,
                    noteMode = noteMode,
                    refreshDaysLine = refreshDaysLine,
                    selectedDateInfo = selectedDateInfo
                )
            },
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
    }
}

private fun isSelectedDateInPast(selectedDateInfo: SelectedDateInfo): Boolean {
    val today = LocalDate.now()
    val selectedDate = selectedDateInfo.getDate()
    if (selectedDate.isBefore(today)) return true
    if (selectedDate.isAfter(today)) return false
    val now = LocalTime.now()
    return now.hour == 23 && now.minute == 59
}
