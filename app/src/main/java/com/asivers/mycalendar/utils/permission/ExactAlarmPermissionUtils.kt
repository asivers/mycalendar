package com.asivers.mycalendar.utils.permission

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat

fun isNeededToRequestScheduleExactAlarmPermission(ctx: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return false
    val alarmManager = ContextCompat.getSystemService(ctx, AlarmManager::class.java)
    return alarmManager?.canScheduleExactAlarms() != true
}
