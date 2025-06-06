package com.asivers.mycalendar.utils

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Density
import com.asivers.mycalendar.constants.schemes.size.FONT_SCHEME_BIG
import com.asivers.mycalendar.constants.schemes.size.FONT_SCHEME_SMALL
import com.asivers.mycalendar.constants.schemes.size.HORIZONTAL_SCHEME_BIG
import com.asivers.mycalendar.constants.schemes.size.HORIZONTAL_SCHEME_SMALL
import com.asivers.mycalendar.constants.schemes.size.VERTICAL_SCHEME_BIG
import com.asivers.mycalendar.constants.schemes.size.VERTICAL_SCHEME_SMALL
import com.asivers.mycalendar.data.scheme.size.FontSizeDpScheme
import com.asivers.mycalendar.data.scheme.size.FontSizeSpScheme
import com.asivers.mycalendar.data.scheme.size.HorizontalSizeScheme
import com.asivers.mycalendar.data.scheme.size.SizeScheme
import com.asivers.mycalendar.data.scheme.size.VerticalSizeScheme

fun getSizeScheme(config: Configuration, density: Density): SizeScheme {
    val horizontalSizeScheme: HorizontalSizeScheme = getHorizontalSizeScheme(config.screenWidthDp)
    val verticalSizeScheme: VerticalSizeScheme = getVerticalSizeScheme(config.screenHeightDp)
    val fontSizeDpScheme: FontSizeDpScheme = getFontSizeDpScheme(config)
    val fontSizeSpScheme: FontSizeSpScheme = getFontSizeSpScheme(fontSizeDpScheme, density)
    return SizeScheme(
        horizontal = horizontalSizeScheme,
        vertical = verticalSizeScheme,
        font = fontSizeSpScheme
    )
}

private fun getHorizontalSizeScheme(screenWidthDp: Int): HorizontalSizeScheme {
    if (screenWidthDp > 400) {
        return HORIZONTAL_SCHEME_BIG
    } else {
        return HORIZONTAL_SCHEME_SMALL
    }
}

private fun getVerticalSizeScheme(screenHeightDp: Int): VerticalSizeScheme {
    if (screenHeightDp > 900) {
        return VERTICAL_SCHEME_BIG
    } else {
        return VERTICAL_SCHEME_SMALL
    }
}

private fun getFontSizeDpScheme(config: Configuration): FontSizeDpScheme {
    if (config.screenWidthDp > 400 && config.screenHeightDp > 900) {
        return FONT_SCHEME_BIG
    } else {
        return FONT_SCHEME_SMALL
    }
}

private fun getFontSizeSpScheme(dpScheme: FontSizeDpScheme, density: Density): FontSizeSpScheme {
    return FontSizeSpScheme(
        main = with(density) { dpScheme.main.toSp() },
        dropdownHeader = with(density) { dpScheme.dropdownHeader.toSp() },
        dropdownItem = with(density) { dpScheme.dropdownItem.toSp() },
        mvHeaderWeek = with(density) { dpScheme.mvHeaderWeek.toSp() },
        yvMonthName = with(density) { dpScheme.yvMonthName.toSp() },
        yvHeaderWeek = with(density) { dpScheme.yvHeaderWeek.toSp() },
        yvDay = with(density) { dpScheme.yvDay.toSp() }
    )
}

fun getIndentFromHeaderDp(screenHeightDp: Int): Int {
    // todo adapt after year view button height is added to size scheme
    // todo add to the new scheme
    // 112 (fixed value) = 48 (settings header) + 64 (year view button)
    return (screenHeightDp - 112) / 24
}

@Composable
fun getInsetsVerticalPaddingDp(): Float {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        val insets = WindowInsets.systemBars.asPaddingValues()
        return (insets.calculateTopPadding() + insets.calculateBottomPadding()).value
    } else {
        return 0f
    }
}
