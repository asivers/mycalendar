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
import com.example.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.example.mycalendar.utils.getCurrentMonthIndex
import com.example.mycalendar.utils.getCurrentYear
import com.example.mycalendar.views.month.MonthViewContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MonthViewContent(
                        modifier = Modifier.padding(innerPadding),
                        year = getCurrentYear(),
                        monthIndex = getCurrentMonthIndex(),
                        holidaysInfo = DEFAULT_HOLIDAYS_INFO
                    )
                }
            }
        }
    }
}
