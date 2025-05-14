package com.asivers.mycalendar.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import com.asivers.mycalendar.data.NotificationTime
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.receivers.AlarmReceiver
import com.asivers.mycalendar.utils.date.getClosestLeapYear
import java.time.LocalDateTime
import java.time.ZonedDateTime

const val NOTIFICATION_CHANNEL_ID = "my_calendar_notification_channel_id"
const val ALARM_ACTION = "alarm_action"
const val ALARM_MESSAGE_EXTRA = "alarm_message"
const val IS_EVERY_YEAR_EXTRA = "is_every_year"
const val NOTE_ID_EXTRA = "note_id"

fun createNotificationChannel(ctx: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = NOTIFICATION_CHANNEL_ID
        val channelName = "My Calendar notification channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "My Calendar notification channel"
            vibrationPattern = LongArray(120) { 500 }
        }
        channel.setSound(
            RingtoneManager.getActualDefaultRingtoneUri(ctx, RingtoneManager.TYPE_RINGTONE),
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
        )
        val notificationManager = ContextCompat.getSystemService(ctx, NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)
    }
}

fun setExactAlarm(
    ctx: Context,
    selectedDateInfo: SelectedDateInfo,
    noteId: Int,
    alarmMessage: String,
    isEveryYear: Boolean,
    notificationTime: NotificationTime
): Boolean {
    val nextAlarmTime = getNextAlarmTime(selectedDateInfo, isEveryYear, notificationTime)
    if (nextAlarmTime == null) {
        Toast.makeText(ctx, "It is not possible to set alarm in the past", Toast.LENGTH_LONG)
            .show()
        return false
    }
    val alarmTimeInMillis = nextAlarmTime.toEpochSecond(ZonedDateTime.now().offset) * 1000

    val pendingIntent = getPendingIntent(ctx, noteId, alarmMessage, isEveryYear)
    val alarmManager = ContextCompat.getSystemService(ctx, AlarmManager::class.java) ?: return false
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()) {
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager, AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent)
    }
    return true
}

fun resetExactAlarmForNextYear(
    ctx: Context,
    noteId: Int,
    alarmMessage: String,
) {
    val now = LocalDateTime.now()
    val isFebruary29 = now.monthValue == 2 && now.dayOfMonth == 29
    val plusYears = if (isFebruary29) getClosestLeapYear(now.year + 1) - now.year else 1
    val nextAlarmTimeInMillis = now
        .plusYears(plusYears.toLong())
        .toEpochSecond(ZonedDateTime.now().offset) * 1000
    val pendingIntent = getPendingIntent(ctx, noteId, alarmMessage, true)
    val alarmManager = ContextCompat.getSystemService(ctx, AlarmManager::class.java) ?: return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()) {
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager, AlarmManager.RTC_WAKEUP, nextAlarmTimeInMillis, pendingIntent)
    }
}

fun cancelExactAlarmIfExists(
    ctx: Context,
    noteId: Int
) {
    val pendingIntent = getPendingIntent(ctx, noteId)
    val alarmManager = ContextCompat.getSystemService(ctx, AlarmManager::class.java) ?: return
    alarmManager.cancel(pendingIntent)
}

private fun getNextAlarmTime(
    selectedDateInfo: SelectedDateInfo,
    isEveryYear: Boolean,
    notificationTime: NotificationTime
): LocalDateTime? {
    val now = LocalDateTime.now()
    if (!isEveryYear) {
        val dateOfNextAlarm = LocalDateTime.of(
            selectedDateInfo.year,
            selectedDateInfo.monthValue,
            selectedDateInfo.dayOfMonth,
            notificationTime.hour,
            notificationTime.minute
        )
        return if (dateOfNextAlarm.isAfter(now)) dateOfNextAlarm else null
    }
    val isFebruary29 = selectedDateInfo.monthValue == 2 && selectedDateInfo.dayOfMonth == 29
    val dateOfNextAlarm = LocalDateTime.of(
        if (isFebruary29) getClosestLeapYear(now.year) else now.year,
        selectedDateInfo.monthValue,
        selectedDateInfo.dayOfMonth,
        notificationTime.hour,
        notificationTime.minute
    )
    return if (dateOfNextAlarm.isAfter(now)) dateOfNextAlarm else {
        LocalDateTime.of(
            if (isFebruary29) getClosestLeapYear(now.year + 1) else now.year + 1,
            selectedDateInfo.monthValue,
            selectedDateInfo.dayOfMonth,
            notificationTime.hour,
            notificationTime.minute
        )
    }
}

private fun getPendingIntent(
    ctx: Context,
    noteId: Int,
    alarmMessage: String? = null,
    isEveryYear: Boolean = false
): PendingIntent {
    val alarmIntent = Intent(ctx, AlarmReceiver::class.java).apply {
        action = ALARM_ACTION
        if (alarmMessage != null) {
            putExtra(ALARM_MESSAGE_EXTRA, alarmMessage)
            putExtra(IS_EVERY_YEAR_EXTRA, isEveryYear)
            putExtra(NOTE_ID_EXTRA, noteId)
        }
    }
    val flag1 = PendingIntent.FLAG_CANCEL_CURRENT
    val flag2 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_IMMUTABLE
    return PendingIntent.getBroadcast(ctx, noteId, alarmIntent, flag1 or flag2)
}
