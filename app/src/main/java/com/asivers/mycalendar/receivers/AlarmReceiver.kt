package com.asivers.mycalendar.receivers

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.asivers.mycalendar.MainActivity
import com.asivers.mycalendar.R
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.utils.notification.ALARM_ACTION
import com.asivers.mycalendar.utils.notification.ALARM_MESSAGE_EXTRA
import com.asivers.mycalendar.utils.notification.IS_EVERY_YEAR_EXTRA
import com.asivers.mycalendar.utils.notification.NOTE_ID_EXTRA
import com.asivers.mycalendar.utils.notification.getFlagsForNotificationChannel
import com.asivers.mycalendar.utils.notification.getNotificationChannelId
import com.asivers.mycalendar.utils.notification.isNeededToRequestScheduleExactAlarmPermission
import com.asivers.mycalendar.utils.notification.resetAllAlarms
import com.asivers.mycalendar.utils.notification.resetExactAlarmForNextYear
import com.asivers.mycalendar.utils.proto.editNote
import java.time.LocalDate

const val JUMP_TO_DATE = "JUMP_TO_DATE"

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context?, intent: Intent?) {
        if (ctx == null || intent == null) return
        when (intent.action) {
            ALARM_ACTION -> {
                val noteId = intent.getIntExtra(NOTE_ID_EXTRA, 0)
                val alarmMessage = intent.getStringExtra(ALARM_MESSAGE_EXTRA) ?: ""
                val isEveryYear = intent.getBooleanExtra(IS_EVERY_YEAR_EXTRA, false)

                val notificationsAllowed = (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                        || PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                            ctx, Manifest.permission.POST_NOTIFICATIONS)

                if (!notificationsAllowed) {
                    if (isEveryYear) {
                        resetExactAlarmForNextYear(ctx, noteId, alarmMessage)
                    }
                    return
                }

                val selectedDateInfo = SelectedDateInfo(LocalDate.now())
                val mainActivityIntent = Intent(ctx, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    putExtra(JUMP_TO_DATE, selectedDateInfo.toShortString())
                }
                val pendingIntent: PendingIntent = PendingIntent.getActivity(
                    ctx, 0, mainActivityIntent, PendingIntent.FLAG_IMMUTABLE)

                val notificationChannelId = getNotificationChannelId(ctx)
                val notification = NotificationCompat.Builder(ctx, notificationChannelId)
                    .setSmallIcon(R.drawable.notification_calendar)
                    .setContentTitle("Calendar Offline Notification")
                    .setContentText(alarmMessage)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setStyle(NotificationCompat.BigTextStyle())
                    .build()
                    .apply {
                        flags = getFlagsForNotificationChannel(notificationChannelId)
                    }
                NotificationManagerCompat.from(ctx).notify(noteId, notification)

                if (isEveryYear) {
                    resetExactAlarmForNextYear(ctx, noteId, alarmMessage)
                } else {
                    editNote(
                        ctx = ctx,
                        selectedDateInfo = selectedDateInfo,
                        id = noteId,
                        msg = alarmMessage,
                        isEveryYear = false,
                        notificationTime = null
                    )
                }
            }
            Intent.ACTION_TIMEZONE_CHANGED,
            Intent.ACTION_TIME_CHANGED -> {
                if (!isNeededToRequestScheduleExactAlarmPermission(ctx)) {
                    resetAllAlarms(ctx = ctx, cancelExistingAlarms = true)
                }
            }
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_BOOT_COMPLETED,
            "android.intent.action.QUICKBOOT_POWERON" -> {
                if (!isNeededToRequestScheduleExactAlarmPermission(ctx)) {
                    resetAllAlarms(ctx = ctx, cancelExistingAlarms = false)
                }
            }
            else -> {}
        }
    }

}
