package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.utils.noRippleClickable
import com.asivers.mycalendar.utils.proto.removeNote
import com.asivers.mycalendar.utils.withAlpha
import com.asivers.mycalendar.utils.withDragToRight

@Composable
fun ExistingNotes(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    onClickToNote: (NoteInfo) -> Unit,
    refreshDaysLine: () -> Unit,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    // todo check that it fits for different screen sizes
    val maxExistingNotesHeightDp = LocalConfiguration.current.screenHeightDp - 380
    val ctx = LocalContext.current
    LazyColumn(
        modifier = modifier
            .heightIn(0.dp, maxExistingNotesHeightDp.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mutableNotes, key = { it.id }) { noteInfo ->
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                // todo adapt for different size schemes
                val maxNoteHeight = maxOf(48, maxExistingNotesHeightDp / mutableNotes.size)
                val horizontalOffset = remember { mutableFloatStateOf(0f) }
                OneSavedNote(
                    modifier = Modifier
                        .heightIn(0.dp, maxNoteHeight.dp)
                        .noRippleClickable { onClickToNote(noteInfo) }
                        .withDragToRight(horizontalOffset, 125f),
                    noteInfo = noteInfo,
                    schemes = schemes
                )
                if (horizontalOffset.floatValue == 125f) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        modifier = Modifier
                            .size(32.dp)
                            .noRippleClickable {
                                removeNote(ctx, selectedDateInfo, noteInfo.id)
                                mutableNotes.remove(noteInfo)
                                refreshDaysLine()
                            },
                        contentDescription = "Delete",
                        tint = schemes.color.text
                    )
                }
            }
        }
    }
}

@Composable
fun OneSavedNote(
    modifier: Modifier = Modifier,
    noteInfo: NoteInfo,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = schemes.color.text.withAlpha(0.2f)) // todo adapt for different color schemes
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = noteInfo.msg,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.dropdownItem,
            color = schemes.color.text,
        )
        if (noteInfo.isEveryYear || noteInfo.notificationTime != null) {
            Spacer(modifier = Modifier.width(12.dp))
        }
        if (noteInfo.isEveryYear) {
            Icon(
                imageVector = Icons.Filled.Refresh, // todo use custom icon
                modifier = Modifier
                    .padding(4.dp, 0.dp, 0.dp, 0.dp)
                    .size(24.dp),
                contentDescription = "Every year note icon",
                tint = schemes.color.text
            )
        }
        if (noteInfo.notificationTime != null) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                modifier = Modifier
                    .padding(4.dp, 0.dp, 0.dp, 0.dp)
                    .size(24.dp),
                contentDescription = "Note with notification icon",
                tint = schemes.color.text
            )
        }
    }
}
