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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalendar.ui.theme.CustomColor
import com.example.mycalendar.constants.TRANSPARENT_BUTTON_COLORS

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
                        CustomColor.Mv_gradient_year_view_button_top,
                        CustomColor.Mv_gradient_year_view_button_bottom
                    )
                )
            ),
        colors = TRANSPARENT_BUTTON_COLORS
    ) {
        Text(
            text = "Year View",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = CustomColor.White,
            textAlign = TextAlign.Center
        )
    }
}
