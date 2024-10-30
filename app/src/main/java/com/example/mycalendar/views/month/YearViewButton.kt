package com.example.mycalendar.views.month

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.mycalendar.ui.theme.CustomColor

@Composable
fun YearViewButton() {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CustomColor.Mv_gradient_year_view_button_top,
                        CustomColor.Mv_gradient_year_view_button_bottom
                    )
                )
            )
    ) {
        Text(
            text = "Year View",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = CustomColor.White,
            textAlign = TextAlign.Center,
        )
    }
}
