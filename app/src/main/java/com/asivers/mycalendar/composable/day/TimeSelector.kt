package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.data.CacheNotificationTime
import com.asivers.mycalendar.data.NotificationTime
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.utils.noRippleClickable

@Composable
fun TimeSelector(
    modifier: Modifier = Modifier,
    onSelection: (NotificationTime) -> Unit,
    cacheNotificationTime: CacheNotificationTime,
    schemes: SchemeContainer
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.height(150.dp) // item height * 3
        ) {
            TimeUnitWheel(
                onItemSelected = { cacheNotificationTime.hour = it },
                numberOfItems = 24,
                initialSelectedItem = cacheNotificationTime.hour,
                schemes = schemes
            )
            TimeUnitWheel(
                onItemSelected = { cacheNotificationTime.minute = it },
                numberOfItems = 60,
                initialSelectedItem = cacheNotificationTime.minute,
                schemes = schemes
            )
        }
        Icon(
            imageVector = Icons.Filled.Done,
            modifier = Modifier
                .padding(9.dp)
                .size(32.dp) // total height 50
                .noRippleClickable {
                    onSelection(cacheNotificationTime.toNotificationTime())
                },
            contentDescription = "Time selection button",
            tint = schemes.color.viewsBottom
        )
    }

}
