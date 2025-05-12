package com.asivers.mycalendar.composable.day

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.asivers.mycalendar.composable.dialog.AlarmPermissionDialog
import com.asivers.mycalendar.composable.dialog.PermissionDeniedDialog
import com.asivers.mycalendar.data.MutableNoteInfo
import com.asivers.mycalendar.data.NotificationTime
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.utils.date.isInFuture
import com.asivers.mycalendar.utils.permission.isNeededToRequestNotificationPermission
import com.asivers.mycalendar.utils.permission.isNeededToRequestScheduleExactAlarmPermission
import com.asivers.mycalendar.utils.permission.isNotificationPermissionNotGranted
import com.asivers.mycalendar.utils.permission.requestNotificationPermission
import com.asivers.mycalendar.utils.permission.wasNotificationPermissionDeniedBefore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime

@Composable
fun SetNotificationDialog(
    modifier: Modifier = Modifier,
    dialogOpened: MutableState<Boolean>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    onNotificationTimeRefreshed: () -> Unit,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val waitNotificationPermissionState = remember(selectedDateInfo) { mutableStateOf(false) }
    if (waitNotificationPermissionState.value) {
        NotificationPermissionWaiter(
            onPermissionGrantedOrDenied = {
                waitNotificationPermissionState.value = false
                dialogOpened.value = true
            }
        )
    }
    val waitAlarmPermissionState = remember(selectedDateInfo) { mutableStateOf(false) }
    if (waitAlarmPermissionState.value) {
        AlarmPermissionWaiter(
            onPermissionGranted = {
                waitAlarmPermissionState.value = false
                dialogOpened.value = true
            },
            ctx = ctx
        )
    }
    val shouldCompareToCurrentTime = !mutableNoteInfo.value.isEveryYear
            && selectedDateInfo.isToday()
    if (dialogOpened.value) {
        if (isNeededToRequestNotificationPermission(ctx)) {
            if (wasNotificationPermissionDeniedBefore(ctx)) {
                PermissionDeniedDialog(
                    onCloseDialog = { dialogOpened.value = false },
                    schemes = schemes
                )
                return
            }
            requestNotificationPermission()
            waitNotificationPermissionState.value = true
            dialogOpened.value = false
            return
        }
        if (isNeededToRequestScheduleExactAlarmPermission(ctx)) {
            AlarmPermissionDialog(
                onStartPermissionIntent = { waitAlarmPermissionState.value = true },
                onCloseDialog = { dialogOpened.value = false },
                schemes = schemes
            )
            return
        }
        Dialog(
            onDismissRequest = { dialogOpened.value = false }
        ) {
            Card(
                modifier = modifier
                    .height(200.dp) // item height * 3 + 50
                    .width(160.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                val notificationTimeState = remember(selectedDateInfo) {
                    mutableStateOf(mutableNoteInfo.value.notificationTime?.copy()
                        ?: if (shouldCompareToCurrentTime)
                            NotificationTime(LocalTime.now().plusMinutes(1))
                        else
                            NotificationTime(0, 0))
                }
                TimeSelector(
                    notificationTimeState = notificationTimeState,
                    onConfirm = {
                        if (!shouldCompareToCurrentTime || isInFuture(it)) {
                            mutableNoteInfo.value = mutableNoteInfo.value
                                .refreshNotificationTime(it)
                        } else {
                            mutableNoteInfo.value = mutableNoteInfo.value
                                .refreshNotificationTime(null)
                            Toast.makeText(
                                ctx,
                                schemes.translation.alarmInPastToast,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        dialogOpened.value = false
                        onNotificationTimeRefreshed()
                    },
                    shouldCompareToCurrentTime = shouldCompareToCurrentTime,
                    schemes = schemes
                )
            }
        }
    }
}

@Composable
fun NotificationPermissionWaiter(
    onPermissionGrantedOrDenied: () -> Unit
) {
    LaunchedEffect(Unit) {
        launch {
            while (isNotificationPermissionNotGranted()) {
                delay(100)
            }
            onPermissionGrantedOrDenied()
        }
    }
}

@Composable
fun AlarmPermissionWaiter(
    onPermissionGranted: () -> Unit,
    ctx: Context
) {
    LaunchedEffect(Unit) {
        launch {
            while (isNeededToRequestScheduleExactAlarmPermission(ctx)) {
                delay(100)
            }
            onPermissionGranted()
        }
    }
}
