package com.asivers.mycalendar.constants

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Brush
import com.asivers.mycalendar.ui.theme.custom.CustomColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

val TRANSPARENT_BUTTON_COLORS: ButtonColors = ButtonColors(
    CustomColor.TRANSPARENT,
    CustomColor.TRANSPARENT,
    CustomColor.TRANSPARENT,
    CustomColor.TRANSPARENT
)

val MONTH_VIEW_BACKGROUND_GRADIENT: Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to CustomColor.MV_GRADIENT_TOP,
        0.1f to CustomColor.MV_GRADIENT_TOP,
        0.25f to CustomColor.MV_GRADIENT_BOTTOM,
        1f to CustomColor.MV_GRADIENT_BOTTOM,
    )
)

val YEAR_VIEW_BACKGROUND_GRADIENT: Brush = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to CustomColor.YV_GRADIENT_TOP,
        0.2f to CustomColor.YV_GRADIENT_CENTER,
        1f to CustomColor.YV_GRADIENT_BOTTOM,
    )
)

val NO_RIPPLE_INTERACTION_SOURCE = object : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}
