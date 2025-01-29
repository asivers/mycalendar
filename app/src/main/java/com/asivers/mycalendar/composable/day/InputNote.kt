package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer

@Composable
fun InputNote(
    modifier: Modifier = Modifier,
    msg: MutableState<String>,
    schemes: SchemeContainer
) {
    val focusRequester = remember { FocusRequester() }
    TextField(
        value = msg.value,
        onValueChange = { msg.value = it },
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 12.dp)
            .focusRequester(focusRequester),
        colors = defaultInputNoteColors(schemes),
        textStyle = TextStyle(
            fontSize = schemes.size.font.dropdownItem,
            fontFamily = MONTSERRAT_MEDIUM
        )
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
