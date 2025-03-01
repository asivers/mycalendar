package com.asivers.mycalendar.composable.month

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.asivers.mycalendar.utils.noRippleClickable
import com.asivers.mycalendar.utils.onHorizontalSwipe

@Composable
fun ClickableSpacers(
    modifier: Modifier = Modifier,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {
    val horizontalOffset = remember { mutableFloatStateOf(0f) }
    Row(
        modifier = modifier.onHorizontalSwipe(horizontalOffset, onClickRight, onClickLeft)
    ) {
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
