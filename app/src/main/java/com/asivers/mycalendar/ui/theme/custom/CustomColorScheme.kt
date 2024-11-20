package com.asivers.mycalendar.ui.theme.custom

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
class CustomColorScheme(
    val mvLight: Color,
    val myvDark: Color,
    val mvBtnLight: Color,
    val mvBtnDark: Color,
    val yvVeryLight: Color,
    val yvLight: Color,
    val yvMedium: Color
)

val summerColorScheme = CustomColorScheme(
    mvLight = Color(0xFF5DA493),
    myvDark = Color(0xFF337623),
    mvBtnLight = Color(0xFF92D700),
    mvBtnDark = Color(0xFF5B9F14),
    yvVeryLight = Color(0xFFADFF00),
    yvLight = Color(0xFF91D600),
    yvMedium = Color(0xFF5CA013)
)

val autumnColorScheme = CustomColorScheme(
    mvLight = Color(0xFF9FA45D),
    myvDark = Color(0xFF86400D),
    mvBtnLight = Color(0xFFFCA600),
    mvBtnDark = Color(0xFFA76A09),
    yvVeryLight = Color(0xFFFCF200),
    yvLight = Color(0xFFFCA600),
    yvMedium = Color(0xFFB87C04)
)

val winterColorScheme = CustomColorScheme(
    mvLight = Color(0xFFA45D5D),
    myvDark = Color(0xFF116446),
    mvBtnLight = Color(0xFF44B9DD),
    mvBtnDark = Color(0xFF217E75),
    yvVeryLight = Color(0xFF5BFEF4),
    yvLight = Color(0xFF43B8DD),
    yvMedium = Color(0xFF248683)
)

val springColorScheme = CustomColorScheme(
    mvLight = Color(0xFF5D82A4),
    myvDark = Color(0xFF52580B),
    mvBtnLight = Color(0xFF28E125),
    mvBtnDark = Color(0xFF418F15),
    yvVeryLight = Color(0xFF4AFF46),
    yvLight = Color(0xFF28E125),
    yvMedium = Color(0xFF3C9D17)
)
