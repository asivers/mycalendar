package com.asivers.mycalendar.ui.theme.custom

import android.content.res.Configuration
import androidx.compose.ui.unit.Density

lateinit var sizeScheme: CustomSizeScheme
    private set

fun setCustomSizeScheme(config: Configuration, density: Density) {
    val horizontalSizeScheme: CustomHorizontalSizeScheme = getHorizontalSizeScheme(config.screenWidthDp)
    val verticalSizeScheme: CustomVerticalSizeScheme = getVerticalSizeScheme(config.screenHeightDp)
    val fontSizeDpScheme: CustomFontSizeDpScheme = getFontSizeDpScheme(config)
    val fontSizeSpScheme: CustomFontSizeSpScheme = getFontSizeSpScheme(fontSizeDpScheme, density)
    sizeScheme = CustomSizeScheme(
        horizontal = horizontalSizeScheme,
        vertical = verticalSizeScheme,
        font = fontSizeSpScheme
    )
}

data class CustomSizeScheme(
    val horizontal: CustomHorizontalSizeScheme,
    val vertical: CustomVerticalSizeScheme,
    val font: CustomFontSizeSpScheme
)
