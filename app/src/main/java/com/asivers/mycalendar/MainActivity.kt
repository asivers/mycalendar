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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.month.MonthView
import com.asivers.mycalendar.composable.year.YearView
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.utils.getColorScheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getHolidaysForCountry
import com.asivers.mycalendar.utils.getMonthViewBackgroundGradient
import com.asivers.mycalendar.utils.getSizeScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val holidaysForCountry = getHolidaysForCountry(Country.SPAIN_REUS, applicationContext)
                val sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)
                val selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) }
                val colorScheme = getColorScheme(selectedMonthIndex.intValue)
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .background(brush = getMonthViewBackgroundGradient(colorScheme)
                    )
                ) {
                    val selectedYear = remember { mutableIntStateOf(getCurrentYear()) }
                    val showYearView = remember { mutableStateOf(false) }
                    val lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) }
                    if (showYearView.value) {
                        val screenHeightDp = LocalConfiguration.current.screenHeightDp
                        val paddingTopDp = (screenHeightDp - 32) / 16.5
                        YearView(
                            modifier = Modifier.padding(0.dp, paddingTopDp.dp, 0.dp, 0.dp),
                            selectedYear = selectedYear,
                            selectedMonthIndex = selectedMonthIndex,
                            showYearView = showYearView,
                            lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                            holidaysForCountry = holidaysForCountry,
                            colorScheme = colorScheme,
                            sizeScheme = sizeScheme
                        )
                    } else {
                        MonthView(
                            selectedYear = selectedYear,
                            selectedMonthIndex = selectedMonthIndex,
                            showYearView = showYearView,
                            lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                            holidaysForCountry = holidaysForCountry,
                            colorScheme = colorScheme,
                            sizeScheme = sizeScheme
                        )
                    }
                }
            }
        }
    }
}
