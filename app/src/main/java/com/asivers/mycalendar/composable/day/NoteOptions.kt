package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.CacheNotificationTime
import com.asivers.mycalendar.data.MutableNoteInfo
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.getOnCompleteOneNoteMode

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
        val mutableNoteInfoValue = mutableNoteInfo.value
        val enabled = mutableNoteInfoValue.msg.isNotBlank()
        SwitchWithLabel(
            modifier = Modifier
                .weight(3f)
                .alpha(if (enabled) 1f else 0.4f),
            checked = mutableNoteInfoValue.isEveryYear,
            onCheckedChange = {
                mutableNoteInfo.value = mutableNoteInfoValue.refreshIsEveryYear(it)
            },
            enabled = enabled,
            label = schemes.translation.switchEveryYear,
            schemes = schemes
        )
        val dialogOpenedState = remember { mutableStateOf(false) }
        SwitchWithLabel(
            modifier = Modifier
                .weight(3f)
                .alpha(if (enabled) 1f else 0.4f),
            checked = mutableNoteInfoValue.notificationTime != null,
            onCheckedChange = { switchedOn ->
                if (switchedOn)
                    dialogOpenedState.value = true
                else
                    mutableNoteInfo.value = mutableNoteInfoValue.refreshNotificationTime(null)
            },
            enabled = enabled,
            label = mutableNoteInfoValue.notificationTime?.toString()
                ?: schemes.translation.switchNotification,
            schemes = schemes
        )
        // todo find the way to separate switchers from the save button, with one-line labels
        // Spacer(modifier = Modifier.weight(1f))
        ActionNoteButton(
            modifier = Modifier
                .weight(3f)
                .alpha(if (enabled) 1f else 0.4f),
            onClick = {
                if (enabled) {
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
        val cacheNotificationTime = remember(selectedDateInfo) {
            CacheNotificationTime(mutableNoteInfoValue.notificationTime)
        }
        if (dialogOpenedState.value) {
            Dialog(
                onDismissRequest = { dialogOpenedState.value = false }
            ) {
                Card(
                    modifier = Modifier
                        .height(200.dp) // item height * 3 + 50
                        .width(160.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    TimeSelector(
                        onSelection = {
                            mutableNoteInfo.value = mutableNoteInfoValue.refreshNotificationTime(it)
                            dialogOpenedState.value = false
                        },
                        cacheNotificationTime = cacheNotificationTime,
                        schemes = schemes
                    )
                }
            }
        }
    }
}

@Composable
fun SwitchWithLabel(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean,
    label: String,
    schemes: SchemeContainer
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Switch(
            modifier = Modifier.scale(0.8f),
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                uncheckedTrackColor = Color.Transparent,
                uncheckedThumbColor = schemes.color.text,
                uncheckedBorderColor = schemes.color.text,
                disabledUncheckedTrackColor = Color.Transparent,
                disabledUncheckedThumbColor = schemes.color.text,
                disabledUncheckedBorderColor = schemes.color.text,
                checkedTrackColor = schemes.color.text,
                checkedThumbColor = schemes.color.viewsTop,
                checkedBorderColor = schemes.color.text,
                disabledCheckedTrackColor = schemes.color.text,
                disabledCheckedThumbColor = schemes.color.viewsTop,
                disabledCheckedBorderColor = schemes.color.text
            )
        )
        Text(
            text = label,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.mvHeaderWeek,
            color = schemes.color.text,
            textAlign = TextAlign.Center
        )
    }
}
