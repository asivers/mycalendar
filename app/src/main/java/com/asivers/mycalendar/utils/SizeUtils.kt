package com.asivers.mycalendar.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Density
import androidx.window.layout.WindowMetricsCalculator
import com.asivers.mycalendar.constants.schemes.size.FONT_SCHEME_BIG
import com.asivers.mycalendar.constants.schemes.size.FONT_SCHEME_SMALL
import com.asivers.mycalendar.constants.schemes.size.FONT_SCHEME_VERY_BIG
import com.asivers.mycalendar.constants.schemes.size.FONT_SCHEME_VERY_SMALL
import com.asivers.mycalendar.constants.schemes.size.HORIZONTAL_SCHEME_BIG
import com.asivers.mycalendar.constants.schemes.size.HORIZONTAL_SCHEME_SMALL
import com.asivers.mycalendar.constants.schemes.size.HORIZONTAL_SCHEME_VERY_BIG
import com.asivers.mycalendar.constants.schemes.size.VERTICAL_SCHEME_BIG
import com.asivers.mycalendar.constants.schemes.size.VERTICAL_SCHEME_SMALL
import com.asivers.mycalendar.constants.schemes.size.VERTICAL_SCHEME_VERY_BIG
import com.asivers.mycalendar.constants.schemes.size.VERTICAL_SCHEME_VERY_SMALL
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
    return if (screenWidthDp > 750)
        HORIZONTAL_SCHEME_VERY_BIG
    else if (screenWidthDp > 400)
        HORIZONTAL_SCHEME_BIG
    else
        HORIZONTAL_SCHEME_SMALL
}

private fun getVerticalSizeScheme(screenHeightDp: Int): VerticalSizeScheme {
    return if (screenHeightDp > 1200)
        VERTICAL_SCHEME_VERY_BIG
    else if (screenHeightDp > 900)
        VERTICAL_SCHEME_BIG
    else if (screenHeightDp > 600)
        VERTICAL_SCHEME_SMALL
    else
        VERTICAL_SCHEME_VERY_SMALL
}

private fun getFontSizeDpScheme(config: Configuration): FontSizeDpScheme {
    return if (config.screenWidthDp > 750 && config.screenHeightDp > 1200)
        FONT_SCHEME_VERY_BIG
    else if (config.screenWidthDp > 400 && config.screenHeightDp > 900)
        FONT_SCHEME_BIG
    else if (config.screenHeightDp > 600)
        FONT_SCHEME_SMALL
    else
        FONT_SCHEME_VERY_SMALL
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

fun getIndentFromHeaderDp(ctx: Context, density: Density): Int {
    val screenHeightDp = getScreenHeightDp(ctx, density)
    // 112 (fixed value) = 48 (settings header) + 64 (year view button)
    return (screenHeightDp - 112) / 24
}

fun getScreenHeightDp(ctx: Context, density: Density): Int {
    val activity = ctx as Activity

    val bounds = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM)
        activity.windowManager.currentWindowMetrics.bounds
    else
        WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity).bounds

    val insets = activity.window.decorView.rootWindowInsets
    val bottomInset = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        insets.getInsetsIgnoringVisibility(android.view.WindowInsets.Type.systemBars()).bottom
    else
        insets.stableInsetBottom

    val heightWithoutBottomInset = bounds.height() - bottomInset
    return with(density) { heightWithoutBottomInset.toDp() }.value.toInt()
}

fun getScreenWidthDp(ctx: Context, density: Density): Int {
    val activity = ctx as Activity
    val bounds = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM)
        activity.windowManager.currentWindowMetrics.bounds
    else
        WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity).bounds
    return with(density) { bounds.width().toDp() }.value.toInt()
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
