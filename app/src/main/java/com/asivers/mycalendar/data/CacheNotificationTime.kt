package com.asivers.mycalendar.data

data class CacheNotificationTime(
    var hour: Int,
    var minute: Int
) {

    constructor(notificationTime: NotificationTime?): this(
        hour = notificationTime?.hour ?: 0,
        minute = notificationTime?.minute ?: 0
    )

    fun toNotificationTime(): NotificationTime = NotificationTime(hour, minute)

}
