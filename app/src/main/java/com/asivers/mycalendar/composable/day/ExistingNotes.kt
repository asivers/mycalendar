package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.utils.noRippleClickable
import com.asivers.mycalendar.utils.proto.removeNote

@Composable
fun ExistingNotes(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    onClickToNote: (NoteInfo) -> Unit,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    // todo check that it fits for different screen sizes
    val maxExistingNotesHeightDp = LocalConfiguration.current.screenHeightDp - 300
    val ctx = LocalContext.current
    LazyColumn(
        modifier = modifier
            .heightIn(0.dp, maxExistingNotesHeightDp.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mutableNotes, key = { it.id }) { noteInfo ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { newValue ->
                    if (newValue == SwipeToDismissBoxValue.StartToEnd) {
                        removeNote(ctx, selectedDateInfo, noteInfo.id)
                        mutableNotes.remove(noteInfo)
                        true
                    } else {
                        false
                    }
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                modifier = Modifier,
                backgroundContent = {
                    if (dismissState.dismissDirection.name == SwipeToDismissBoxValue.StartToEnd.name) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                modifier = Modifier.size(32.dp),
                                contentDescription = "Delete",
                                tint = schemes.color.text
                            )
                        }
                    }
                },
                enableDismissFromEndToStart = false
            ) {
                // todo adapt for different size schemes
                val maxNoteHeight = maxOf(48, maxExistingNotesHeightDp / mutableNotes.size)
                OneSavedNote(
                    modifier = Modifier
                        .heightIn(0.dp, maxNoteHeight.dp)
                        .noRippleClickable { onClickToNote(noteInfo) },
                    msg = noteInfo.msg,
                    schemes = schemes
                )
            }
        }
    }
}

@Composable
fun OneSavedNote(
    modifier: Modifier = Modifier,
    msg: String,
    schemes: SchemeContainer
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color(0x33FFFFFF)) // todo adapt for different color schemes
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = msg,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.dropdownItem,
            color = schemes.color.text,
        )
    }
}
