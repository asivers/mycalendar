package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.NoteMode

@Composable
fun ActionNoteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    noteMode: NoteMode,
    schemes: SchemeContainer
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageVector = if (noteMode == NoteMode.VIEW)
            Icons.Filled.Delete
        else
            Icons.Filled.Done
        Icon(
            imageVector = imageVector,
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .clickable { onClick() },
            contentDescription = "Action note button",
            tint = schemes.color.text
        )
        val text = if (noteMode == NoteMode.VIEW)
            schemes.translation.deleteNote
        else
            schemes.translation.saveNote
        Text(
            text = text,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.yvMonthName,
            color = schemes.color.text,
            textAlign = TextAlign.Center
        )
    }
}
