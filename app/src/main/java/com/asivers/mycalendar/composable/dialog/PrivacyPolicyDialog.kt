package com.asivers.mycalendar.composable.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.utils.getScreenHeightDp
import com.asivers.mycalendar.utils.getScreenWidthDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyDialog(
    modifier: Modifier = Modifier,
    onCloseDialog: () -> Unit,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val density = LocalDensity.current
    val screenHeightDp = getScreenHeightDp(ctx, density)
    val screenWidthDp = getScreenWidthDp(ctx, density)
    BasicAlertDialog(
        modifier = modifier
            .height((screenHeightDp - 150).dp)
            .width((screenWidthDp - 50).dp),
        onDismissRequest = { onCloseDialog() }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column {
                TextField(
                    value = schemes.translation.privacyPolicyContent,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((screenHeightDp - 190).dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(onClick = { onCloseDialog() }) {
                        Text(text = schemes.translation.ok)
                    }
                }
            }
        }
    }
}
