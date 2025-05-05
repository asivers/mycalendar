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
import com.asivers.mycalendar.utils.getOnCompleteOneNoteMode
import java.time.LocalDate

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
        val dateIsNotInPast = !selectedDateInfo.getDate().isBefore(LocalDate.now())
        SwitchWithLabel(
            modifier = Modifier
                .weight(3f)
                .alpha(if (messageIsNotBlank) 1f else 0.4f),
            checked = mutableNoteInfo.value.isEveryYear,
            onCheckedChange = {
                mutableNoteInfo.value = mutableNoteInfo.value.refreshIsEveryYear(it)
            },
            enabled = messageIsNotBlank,
            label = schemes.translation.switchEveryYear,
            schemes = schemes
        )
        val dialogOpened = remember { mutableStateOf(false) }
        SwitchWithLabel(
            modifier = Modifier
                .weight(3f)
                .alpha(if (messageIsNotBlank && dateIsNotInPast) 1f else 0.4f),
            checked = mutableNoteInfo.value.notificationTime != null,
            onCheckedChange = { switchedOn ->
                if (switchedOn) {
                    dialogOpened.value = true
                } else {
                    mutableNoteInfo.value = mutableNoteInfo.value.refreshNotificationTime(null)
                }
            },
            enabled = messageIsNotBlank && dateIsNotInPast,
            label = mutableNoteInfo.value.notificationTime?.toString()
                ?: schemes.translation.switchNotification,
            schemes = schemes
        )
        // todo find the way to separate switchers from the save button, with one-line labels
        // Spacer(modifier = Modifier.weight(1f))
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
        SetNotificationDialog(
            dialogOpened = dialogOpened,
            mutableNoteInfo = mutableNoteInfo,
            selectedDateInfo = selectedDateInfo,
            schemes = schemes
        )
    }
}
