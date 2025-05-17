package com.asivers.mycalendar.utils.notification

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.asivers.mycalendar.enums.PermissionType
import com.asivers.mycalendar.utils.proto.areNotificationsExist

private lateinit var notificationPermissionRequestLauncher: ActivityResultLauncher<String>
private var notificationPermissionResult = NotificationPermissionResult.NOT_GRANTED

private enum class NotificationPermissionResult {
    NOT_GRANTED,
    GRANTED,
    DENIED
}

fun registerNotificationPermissionRequestLauncher(activity: ComponentActivity) {
    if (isNeededToRequestNotificationPermission(activity)) {
        notificationPermissionRequestLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                notificationPermissionResult = if (isGranted)
                    NotificationPermissionResult.GRANTED else NotificationPermissionResult.DENIED
            }
    }
}

fun isNeededToRequestNotificationPermission(ctx: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return false
    val status = ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS)
    return status != PackageManager.PERMISSION_GRANTED
}

fun requestNotificationPermission() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    notificationPermissionRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
}

fun isNotificationPermissionNotGranted(): Boolean {
    return notificationPermissionResult == NotificationPermissionResult.NOT_GRANTED
}

fun wasNotificationPermissionDeniedBefore(ctx: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return false
    val activity = ctx as Activity
    return !activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
}

fun isNeededToRequestScheduleExactAlarmPermission(ctx: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return false
    val alarmManager = ContextCompat.getSystemService(ctx, AlarmManager::class.java)
    return alarmManager?.canScheduleExactAlarms() != true
}

fun getPermissionTypeToShowWarningRevoked(ctx: Context): PermissionType? {
    if (isNeededToRequestScheduleExactAlarmPermission(ctx)) {
        return if (areNotificationsExist(ctx)) PermissionType.EXACT_ALARM else null
    }
    if (isNeededToRequestNotificationPermission(ctx)) {
        return if (areNotificationsExist(ctx)) PermissionType.NOTIFICATION else null
    }
    return null
}
