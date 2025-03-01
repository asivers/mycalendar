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
    onClickRight: () -> Unit,
    swipeEnabled: Boolean = false
) {
    val rowModifier = if (swipeEnabled) {
        val horizontalOffset = remember { mutableFloatStateOf(0f) }
        modifier.onHorizontalSwipe(horizontalOffset, onClickRight, onClickLeft)
    } else {
        modifier
    }

    Row(
        modifier = rowModifier
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
