package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.NoteMode

@Composable
fun InputNote(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    initialMsg: String,
    noteMode: NoteMode,
    schemes: SchemeContainer
) {
    val inputMsg = remember { mutableStateOf(initialMsg) }
    val focusRequester = remember { FocusRequester() }
    TextField(
        value = inputMsg.value,
        onValueChange = {
            onValueChange(it)
            inputMsg.value = it
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused && noteMode == NoteMode.VIEW) onClick()
            },
        colors = defaultInputNoteColors(schemes),
        textStyle = TextStyle(
            fontSize = schemes.size.font.dropdownItem,
            fontFamily = MONTSERRAT_MEDIUM
        )
    )
    LaunchedEffect(Unit) {
        if (noteMode == NoteMode.ADD) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
fun defaultInputNoteColors(
    schemes: SchemeContainer
): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedTextColor = schemes.color.text,
        unfocusedTextColor = schemes.color.text,
        disabledTextColor = schemes.color.text,
        errorTextColor = schemes.color.text,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
    )
}
