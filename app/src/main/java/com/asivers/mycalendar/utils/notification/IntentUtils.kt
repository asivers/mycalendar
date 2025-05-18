package com.asivers.mycalendar.utils.notification

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import com.asivers.mycalendar.enums.PermissionType

fun startRequestPermissionIntent(ctx: Context, permissionType: PermissionType) {
    if (permissionType == PermissionType.EXACT_ALARM)
        startRequestExactAlarmPermissionIntent(ctx)
    else
        startRequestNotificationPermissionIntent(ctx)
}

fun startRequestExactAlarmPermissionIntent(ctx: Context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return
    val intent = Intent(
        Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
        ("package:" + ctx.packageName).toUri()
    )
    ctx.startActivity(intent)
}

fun startRequestNotificationPermissionIntent(ctx: Context) {
    val intent = Intent()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, ctx.packageName)
    } else {
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS")
        intent.putExtra("app_package", ctx.packageName)
        intent.putExtra("app_uid", ctx.applicationInfo.uid)
    }
    ctx.startActivity(intent)
}
