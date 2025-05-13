package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.NoteMode
import com.asivers.mycalendar.utils.noRippleClickable

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
        val painterResourceId = if (noteMode == NoteMode.VIEW)
            R.drawable.delete_trash_can
        else
            R.drawable.save_check_mark
        Image(
            painter = painterResource(id = painterResourceId),
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .noRippleClickable { onClick() },
            contentDescription = "Action note button",
            colorFilter = ColorFilter.tint(schemes.color.text)
        )
        val text = if (noteMode == NoteMode.VIEW)
            schemes.translation.deleteNote
        else
            schemes.translation.saveNote
        Text(
            text = text,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.mvHeaderWeek,
            color = schemes.color.text,
            textAlign = TextAlign.Center
        )
    }
}
