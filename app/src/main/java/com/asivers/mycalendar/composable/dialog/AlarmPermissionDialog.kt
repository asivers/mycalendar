package com.asivers.mycalendar.composable.dialog

import android.content.Intent
import android.os.Build
import android.provider.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.data.SchemeContainer
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmPermissionDialog(
    modifier: Modifier = Modifier,
    onStartPermissionIntent: () -> Unit,
    onCloseDialog: () -> Unit,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = { onCloseDialog() }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)) {
                Text(text = schemes.translation.requestAlarmPermission)
                Row(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .align(Alignment.End)
                ) {
                    TextButton(onClick = { onCloseDialog() }) {
                        Text(text = schemes.translation.dismiss)
                    }
                    TextButton(
                        onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                val intent = Intent(
                                    Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                                    ("package:" + ctx.packageName).toUri()
                                )
                                ctx.startActivity(intent)
                                onStartPermissionIntent()
                            }
                            onCloseDialog()
                        }
                    ) {
                        Text(text = schemes.translation.confirm)
                    }
                }
            }
        }
    }
}
