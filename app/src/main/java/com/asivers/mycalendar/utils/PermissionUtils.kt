package com.asivers.mycalendar.utils

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun requestNotificationPermission(ctx: Context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    val activity = ctx as Activity
    val permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    val requestCode = 1
    ActivityCompat.requestPermissions(activity, permissions, requestCode)
}

fun isNeededToRequestNotificationPermission(ctx: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return false
    val status = ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS)
    return status != PackageManager.PERMISSION_GRANTED
}

fun isNeededToRequestScheduleExactAlarmPermission(ctx: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return false
    val alarmManager = ContextCompat.getSystemService(ctx, AlarmManager::class.java)
    return alarmManager?.canScheduleExactAlarms() != true
}
