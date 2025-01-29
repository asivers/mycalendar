package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.utils.proto.addNote

@Composable
fun SaveNoteButton(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    msg: MutableState<String>,
    onExitEditMode: () -> Unit,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    Button(
        onClick = {
            val newNote = addNote(
                ctx = ctx,
                selectedDateInfo = selectedDateInfo,
                msg = msg.value,
                isEveryYear = false,
                isHoliday = false
            )
            mutableNotes.add(newNote)
            onExitEditMode()
        },
        modifier = modifier.fillMaxWidth(),
        colors = getSaveButtonColors(schemes),
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "Save Note",
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.dropdownItem,
            color = schemes.color.text
        )
    }
}

fun getSaveButtonColors(schemes: SchemeContainer): ButtonColors {
    return ButtonColors(
        schemes.color.monthViewTop,
        schemes.color.monthViewTop,
        schemes.color.monthViewTop,
        schemes.color.monthViewTop
    )
}
