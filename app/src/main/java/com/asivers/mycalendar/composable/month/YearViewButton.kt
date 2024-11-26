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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.NO_RIPPLE_INTERACTION_SOURCE
import com.asivers.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.data.scheme.size.SizeScheme
import com.asivers.mycalendar.utils.getSizeScheme

@Preview
@Composable
fun YearViewButtonPreview() {
    YearViewButton(
        showYearView = remember { mutableStateOf(false) },
        colorScheme = SUMMER,
        sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)
    )
}

@Composable
fun YearViewButton(
    modifier: Modifier = Modifier,
    showYearView: MutableState<Boolean>,
    colorScheme: ColorScheme,
    sizeScheme: SizeScheme
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
                        colorScheme.mvBtnLight,
                        colorScheme.mvBtnDark
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
            text = stringResource(id = R.string.year_view),
            fontFamily = MONTSERRAT_BOLD,
            fontSize = sizeScheme.font.main,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
