package com.asivers.mycalendar.composable.month

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.asivers.mycalendar.utils.noRippleClickable

@Composable
fun ClickableSpacers(
    modifier: Modifier,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {
    Row(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(3f)
                .noRippleClickable { onClickLeft() }
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(3f)
                .noRippleClickable { onClickRight() }
        )
    }
}
