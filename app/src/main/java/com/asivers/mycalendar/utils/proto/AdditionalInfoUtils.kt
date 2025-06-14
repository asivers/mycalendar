package com.asivers.mycalendar.utils.proto

import android.content.Context
import com.asivers.mycalendar.serializers.additionalInfoDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun wasNotificationPermissionRequestedBefore(ctx: Context): Boolean {
    return runBlocking { ctx.additionalInfoDataStore.data.first() }
        .wasNotificationPermissionRequestedBefore
}

fun setNotificationPermissionWasRequestedBefore(ctx: Context) {
    runBlocking {
        ctx.additionalInfoDataStore.updateData { currentAdditionalInfo ->
            currentAdditionalInfo.toBuilder()
                .setWasNotificationPermissionRequestedBefore(true)
                .build()
        }
    }
}
