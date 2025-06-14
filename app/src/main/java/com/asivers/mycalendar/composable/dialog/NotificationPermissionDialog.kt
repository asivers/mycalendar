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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.utils.notification.requestNotificationPermission
import com.asivers.mycalendar.utils.notification.setNotificationPermissionNotGranted
import com.asivers.mycalendar.utils.notification.startRequestNotificationPermissionIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationPermissionDialog(
    modifier: Modifier = Modifier,
    onStartRequestingPermission: () -> Unit,
    onCloseDialog: () -> Unit,
    shouldShowRequestPermissionRationale: Boolean,
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
                val explainText = if (shouldShowRequestPermissionRationale)
                    schemes.translation.explainNotificationPermissionNeeded
                else
                    schemes.translation.explainNotificationPermissionDenied
                Text(text = explainText)
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
                            setNotificationPermissionNotGranted()
                            onStartRequestingPermission()
                            onCloseDialog()
                            if (shouldShowRequestPermissionRationale) {
                                requestNotificationPermission()
                            } else {
                                startRequestNotificationPermissionIntent(ctx)
                            }
                        }
                    ) {
                        val confirmButtonText = if (shouldShowRequestPermissionRationale)
                            schemes.translation.confirm else schemes.translation.goToSettings
                        Text(text = confirmButtonText)
                    }
                }
            }
        }
    }
}
