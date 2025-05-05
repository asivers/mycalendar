package com.asivers.mycalendar.composable.day

import android.content.Context
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
import com.asivers.mycalendar.data.CacheNotificationTime
import com.asivers.mycalendar.data.MutableNoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.utils.isNeededToRequestNotificationPermission
import com.asivers.mycalendar.utils.isNeededToRequestScheduleExactAlarmPermission
import com.asivers.mycalendar.utils.requestNotificationPermission
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SetNotificationDialog(
    modifier: Modifier = Modifier,
    dialogOpened: MutableState<Boolean>,
    mutableNoteInfo: MutableState<MutableNoteInfo>,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val waitNotificationPermissionState = remember(selectedDateInfo) { mutableStateOf(false) }
    if (waitNotificationPermissionState.value) {
        NotificationPermissionWaiter(
            onPermissionGranted = {
                waitNotificationPermissionState.value = false
                dialogOpened.value = true
            },
            ctx = ctx
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
    val cacheNotificationTime = remember(selectedDateInfo) {
        CacheNotificationTime(mutableNoteInfo.value.notificationTime)
    }
    if (dialogOpened.value) {
        if (isNeededToRequestNotificationPermission(ctx)) {
            // todo rework using registerForActivityResult and process denial case
            requestNotificationPermission(ctx)
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
                TimeSelector(
                    onSelection = {
                        mutableNoteInfo.value = mutableNoteInfo.value.refreshNotificationTime(it)
                        dialogOpened.value = false
                    },
                    cacheNotificationTime = cacheNotificationTime,
                    schemes = schemes
                )
            }
        }
    }
}

@Composable
fun NotificationPermissionWaiter(
    onPermissionGranted: () -> Unit,
    ctx: Context
) {
    LaunchedEffect(Unit) {
        launch {
            while (isNeededToRequestNotificationPermission(ctx)) {
                delay(100)
            }
            onPermissionGranted()
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
