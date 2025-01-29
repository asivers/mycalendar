package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.utils.proto.removeNote

@Composable
fun ExistingNotes(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val maxItemsDisplayed = 6
    val maxItemHeightDp = 48 // todo adapt for different size schemes
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height((maxItemsDisplayed * maxItemHeightDp).dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mutableNotes, key = { it.id }) { note ->
            val id = note.id
            val msg = note.msg
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { newValue ->
                    if (newValue == SwipeToDismissBoxValue.StartToEnd) {
                        removeNote(ctx, selectedDateInfo, id)
                        mutableNotes.remove(note)
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
                        Row(modifier = Modifier.fillMaxSize().background(Color.Red),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "delete")
                        }
                    }
                },
                enableDismissFromEndToStart = false
            ) {
                OneSavedNote(
                    modifier = Modifier.heightIn(0.dp, maxItemHeightDp.dp),
                    msg = "$id $msg",
                    schemes = schemes
                )
            }
            HorizontalDivider()
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
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = msg,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.dropdownItem,
            color = schemes.color.text,
        )
    }
}
