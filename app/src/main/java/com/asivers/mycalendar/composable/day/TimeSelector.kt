package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.data.NotificationTime
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.utils.date.isInFuture
import com.asivers.mycalendar.utils.noRippleClickable

@Composable
fun TimeSelector(
    modifier: Modifier = Modifier,
    notificationTimeState: MutableState<NotificationTime>,
    onConfirm: (NotificationTime) -> Unit,
    shouldCompareToCurrentTime: Boolean,
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
                onItemSelected = {
                    notificationTimeState.value = NotificationTime(
                        it, notificationTimeState.value.minute)
                },
                numberOfItems = 24,
                initialSelectedItem = notificationTimeState.value.hour,
                schemes = schemes
            )
            TimeUnitWheel(
                onItemSelected = {
                    notificationTimeState.value = NotificationTime(
                        notificationTimeState.value.hour, it)
                },
                numberOfItems = 60,
                initialSelectedItem = notificationTimeState.value.minute,
                schemes = schemes
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .alpha(0.4f),
            color = schemes.color.selectedItemInDropdown
        )
        val isConfirmEnabled = !shouldCompareToCurrentTime
                || isInFuture(notificationTimeState.value)
        Box(
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth()
                .noRippleClickable {
                    if (isConfirmEnabled) onConfirm(notificationTimeState.value)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.save_check_mark),
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 3.dp)
                    .size(32.dp)
                    .alpha(if (isConfirmEnabled) 1f else 0.4f),
                contentDescription = "Time selection button",
                colorFilter = ColorFilter.tint(schemes.color.selectedItemInDropdown)
            )
        }
    }
}
