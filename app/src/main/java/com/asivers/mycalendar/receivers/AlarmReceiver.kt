package com.asivers.mycalendar.receivers

import android.Manifest
import android.app.AlarmManager
import android.app.Notification
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
import com.asivers.mycalendar.utils.ALARM_ACTION
import com.asivers.mycalendar.utils.ALARM_MESSAGE_EXTRA
import com.asivers.mycalendar.utils.IS_EVERY_YEAR_EXTRA
import com.asivers.mycalendar.utils.NOTE_ID_EXTRA
import com.asivers.mycalendar.utils.NOTIFICATION_CHANNEL_ID
import com.asivers.mycalendar.utils.isNeededToRequestScheduleExactAlarmPermission
import com.asivers.mycalendar.utils.resetAllAlarms
import com.asivers.mycalendar.utils.resetExactAlarmForNextYear

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context?, intent: Intent?) {
        // todo check working with old builds
        if (ctx == null || intent == null) return
        when (intent.action) {
            ALARM_ACTION -> {
                val alarmMessage = intent.getStringExtra(ALARM_MESSAGE_EXTRA) ?: ""
                val isEveryYear = intent.getBooleanExtra(IS_EVERY_YEAR_EXTRA, false)
                val noteId = intent.getIntExtra(NOTE_ID_EXTRA, 0)

                val mainActivityIntent = Intent(ctx, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                val pendingIntent: PendingIntent = PendingIntent.getActivity(
                    ctx, 0, mainActivityIntent, PendingIntent.FLAG_IMMUTABLE)

                val builder = NotificationCompat.Builder(ctx, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification_calendar)
                    .setContentTitle("My Calendar Notification")
                    .setContentText(alarmMessage)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)

                val notificationsAllowed = (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) ||
                    ActivityCompat.checkSelfPermission(
                        ctx, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
                if (notificationsAllowed) {
                    val notification = builder.build().apply {
                        flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_INSISTENT
                    }
                    NotificationManagerCompat.from(ctx).notify(noteId, notification)
                }

                if (isEveryYear) {
                    resetExactAlarmForNextYear(ctx, noteId, alarmMessage)
                }
            }
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_BOOT_COMPLETED,
            "android.intent.action.QUICKBOOT_POWERON" -> {
                if (!isNeededToRequestScheduleExactAlarmPermission(ctx)) {
                    resetAllAlarms(ctx)
                }
            }
            else -> {}
        }
    }

}
