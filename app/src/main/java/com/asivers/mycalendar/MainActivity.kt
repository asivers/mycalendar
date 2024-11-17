package com.asivers.mycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.DEFAULT_HOLIDAYS_INFO
import com.asivers.mycalendar.constants.MONTH_VIEW_BACKGROUND_GRADIENT
import com.asivers.mycalendar.ui.theme.MyCalendarTheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.views.month.MonthViewContent
import com.asivers.mycalendar.views.year.YearViewContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCalendarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .padding(innerPadding)
                        .background(brush = MONTH_VIEW_BACKGROUND_GRADIENT)
                    ) {
                        val showYearView = remember { mutableStateOf(false) }
                        val selectedYear = remember { mutableIntStateOf(getCurrentYear()) }
                        if (showYearView.value) {
                            YearViewContent(
                                modifier = Modifier.padding(0.dp, 50.dp, 0.dp, 0.dp),
                                year = selectedYear,
                                showYearView = showYearView,
                                holidaysInfo = DEFAULT_HOLIDAYS_INFO
                            )
                        } else {
                            MonthViewContent(
                                year = getCurrentYear(),
                                monthIndex = getCurrentMonthIndex(),
                                showYearView = showYearView,
                                holidaysInfo = DEFAULT_HOLIDAYS_INFO
                            )
                        }
                    }
                }
            }
        }
    }
}
