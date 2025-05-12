package com.asivers.mycalendar.utils.date

import com.asivers.mycalendar.data.NotificationTime
import com.asivers.mycalendar.data.SelectedDateInfo
import java.time.LocalDate
import java.time.LocalTime

fun isInFuture(notificationTime: NotificationTime): Boolean {
    val nearestTimeInFuture = LocalTime.now().plusMinutes(1)
    if (notificationTime.hour < nearestTimeInFuture.hour) return false
    if (notificationTime.hour > nearestTimeInFuture.hour) return true
    return notificationTime.minute >= nearestTimeInFuture.minute
}

fun isInFuture(
    selectedDateInfo: SelectedDateInfo,
    notificationTime: NotificationTime
): Boolean {
    val today = LocalDate.now()
    val selectedDate = selectedDateInfo.getDate()
    if (selectedDate.isBefore(today)) return false
    if (selectedDate.isAfter(today)) return true
    return isInFuture(notificationTime)
}
