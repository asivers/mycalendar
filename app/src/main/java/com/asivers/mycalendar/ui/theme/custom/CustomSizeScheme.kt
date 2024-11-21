package com.asivers.mycalendar.ui.theme.custom

import android.content.res.Configuration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

lateinit var sizeScheme: CustomSizeScheme
    private set

fun setCustomSizeScheme(config: Configuration, density: Density) {
    val ctx: CustomSizeContext = getSizeContext(config)
    sizeScheme = CustomSizeScheme(ctx, density)
}

private fun getSizeContext(config: Configuration): CustomSizeContext {
    if (config.screenHeightDp > 900) {
        return bigSizeContext
    } else {
        return smallSizeContext
    }
}

class CustomSizeScheme(ctx: CustomSizeContext, density: Density) {

    val font: CustomFontSizeScheme = CustomFontSizeScheme(ctx.font, density)
    val yearDropdownWidth: Dp = ctx.yearDropdownWidth
    val yvMonthHorizontalPadding: Dp = ctx.yvMonthHorizontalPadding
    val yvMonthVerticalPadding: Dp = ctx.yvMonthVerticalPadding

    class CustomFontSizeScheme(ctx: CustomSizeContext.CustomFontSizeContext, density: Density) {
        val main: TextUnit = with(density) { ctx.main.toSp() }
        val dropdownHeader: TextUnit = with(density) { ctx.dropdownHeader.toSp() }
        val monthDropdownItem: TextUnit = with(density) { ctx.monthDropdownItem.toSp() }
        val mvHeaderWeek: TextUnit = with(density) { ctx.mvHeaderWeek.toSp() }
        val yvMonthName: TextUnit = with(density) { ctx.yvMonthName.toSp() }
        val yvHeaderWeek: TextUnit = with(density) { ctx.yvHeaderWeek.toSp() }
        val yvDay: TextUnit = with(density) { ctx.yvDay.toSp() }
    }

}
