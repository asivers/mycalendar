package com.asivers.mycalendar.constants

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

val TRANSPARENT_BUTTON_COLORS: ButtonColors = ButtonColors(
    Color.Transparent,
    Color.Transparent,
    Color.Transparent,
    Color.Transparent
)

val NO_PADDING_TEXT_STYLE: TextStyle = TextStyle(
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

val NO_RIPPLE_INTERACTION_SOURCE = object : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}
