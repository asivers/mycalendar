package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer

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
