package com.example.mycalendar.views.month

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalendar.ui.theme.custom.CustomColor
import com.example.mycalendar.constants.TRANSPARENT_BUTTON_COLORS
import com.example.mycalendar.ui.theme.custom.CustomFont

@Preview
@Composable
fun YearViewButtonPreview() {
    YearViewButton()
}

@Composable
fun YearViewButton() {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp, 20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CustomColor.MV_GRADIENT_YEAR_VIEW_BUTTON_TOP,
                        CustomColor.MV_GRADIENT_YEAR_VIEW_BUTTON_BOTTOM
                    )
                )
            ),
        colors = TRANSPARENT_BUTTON_COLORS
    ) {
        Text(
            text = "Year View",
            fontFamily = CustomFont.MONTSERRAT_BOLD,
            fontSize = 26.sp,
            color = CustomColor.WHITE,
            textAlign = TextAlign.Center
        )
    }
}
