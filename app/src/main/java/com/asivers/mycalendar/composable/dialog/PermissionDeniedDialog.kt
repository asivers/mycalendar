package com.asivers.mycalendar.composable.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.data.SchemeContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDeniedDialog(
    modifier: Modifier = Modifier,
    onCloseDialog: () -> Unit,
    schemes: SchemeContainer
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = { onCloseDialog() }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)) {
                Text(text = schemes.translation.explainPermissionDenied)
                Row(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .align(Alignment.End)
                ) {
                    TextButton(onClick = { onCloseDialog() }) {
                        Text(text = schemes.translation.ok)
                    }
                }
            }
        }
    }
}
