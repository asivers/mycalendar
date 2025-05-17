package com.asivers.mycalendar.utils.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.asivers.mycalendar.enums.NotificationsMode
import com.asivers.mycalendar.utils.proto.getSavedNotificationsMode
import com.asivers.mycalendar.utils.proto.getSavedSettings

const val NOTIFICATION_CHANNEL_WITH_RINGTONE_ID =
    "my_calendar_notification_channel_with_ringtone_id"

const val NOTIFICATION_CHANNEL_WITHOUT_RINGTONE_ID =
    "my_calendar_notification_channel_without_ringtone_id"

const val ALARM_ACTION = "alarm_action"
const val ALARM_MESSAGE_EXTRA = "alarm_message"
const val IS_EVERY_YEAR_EXTRA = "is_every_year"
const val NOTE_ID_EXTRA = "note_id"

fun createNotificationChannels(ctx: Context) {
    createNotificationChannelWithRingtone(ctx)
    createNotificationChannelWithoutRingtone(ctx)
}

fun getNotificationChannelId(ctx: Context): String {
    val savedSettings = getSavedSettings(ctx)
    val savedNotificationsMode = getSavedNotificationsMode(savedSettings)
    return if (savedNotificationsMode == NotificationsMode.WITH_RINGTONE)
        NOTIFICATION_CHANNEL_WITH_RINGTONE_ID
    else
        NOTIFICATION_CHANNEL_WITHOUT_RINGTONE_ID
}

fun getFlagsForNotificationChannel(channelId: String): Int {
    return if (channelId == NOTIFICATION_CHANNEL_WITH_RINGTONE_ID)
        Notification.FLAG_AUTO_CANCEL or Notification.FLAG_INSISTENT
    else
        Notification.FLAG_AUTO_CANCEL
}

private fun createNotificationChannelWithRingtone(ctx: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = NOTIFICATION_CHANNEL_WITH_RINGTONE_ID
        val channelName = "My Calendar notification channel with ringtone"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "My Calendar notification channel with ringtone"
            vibrationPattern = LongArray(120) { 500 }
        }
        channel.setSound(
            RingtoneManager.getActualDefaultRingtoneUri(ctx, RingtoneManager.TYPE_RINGTONE),
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
        )
        ContextCompat.getSystemService(ctx, NotificationManager::class.java)
            ?.createNotificationChannel(channel)
    }
}

private fun createNotificationChannelWithoutRingtone(ctx: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = NOTIFICATION_CHANNEL_WITHOUT_RINGTONE_ID
        val channelName = "My Calendar notification channel without ringtone"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "My Calendar notification channel without ringtone"
            vibrationPattern = longArrayOf(500)
        }
        ContextCompat.getSystemService(ctx, NotificationManager::class.java)
            ?.createNotificationChannel(channel)
    }
}
