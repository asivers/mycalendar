package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.NoteMode

@Composable
fun BottomNoteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    noteMode: NoteMode,
    schemes: SchemeContainer
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        colors = getBottomNoteButtonColors(schemes),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = if (noteMode == NoteMode.VIEW) "Delete Note" else "Save Note",
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.dropdownItem,
            color = schemes.color.text
        )
    }
}

fun getBottomNoteButtonColors(schemes: SchemeContainer): ButtonColors {
    return ButtonColors(
        schemes.color.monthViewTop,
        schemes.color.monthViewTop,
        schemes.color.monthViewTop,
        schemes.color.monthViewTop
    )
}
