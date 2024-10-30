package com.example.mycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.utils.defaultHolidaysInfo
import com.example.mycalendar.views.MonthViewContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MonthViewContent(
                        modifier = Modifier.padding(innerPadding),
                        year = 2024,
                        monthIndex = 10,
                        holidaysInfo = defaultHolidaysInfo
                    )
                }
            }
        }
    }
}
