package com.asivers.mycalendar.composable.month

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_RIPPLE_INTERACTION_SOURCE
import com.asivers.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.utils.getSchemesForPreview

@Preview
@Composable
fun YearViewButtonPreview() {
    YearViewButton(
        showYearView = remember { mutableStateOf(false) },
        schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun YearViewButton(
    modifier: Modifier = Modifier,
    showYearView: MutableState<Boolean>,
    schemes: SchemeContainer
) {
    var offset by remember { mutableFloatStateOf(0f) }
    Button(
        onClick = { showYearView.value = true },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(36.dp, 36.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        schemes.color.mvBtnLight,
                        schemes.color.mvBtnDark
                    )
                )
            )
            .padding(0.dp, 8.dp)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = { offset = 0f },
                    onDragEnd = {
                        if (offset < -20f) {
                            showYearView.value = true
                        }
                    }
                ) { _, dragAmount ->
                    offset += dragAmount
                }
            },
        colors = TRANSPARENT_BUTTON_COLORS,
        interactionSource = NO_RIPPLE_INTERACTION_SOURCE
    ) {
        Text(
            text = schemes.translation.yearView,
            fontFamily = MONTSERRAT_BOLD,
            fontSize = schemes.size.font.main,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
