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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.asivers.mycalendar.composable.month.MonthView
import com.asivers.mycalendar.composable.settings.SettingsView
import com.asivers.mycalendar.composable.year.YearView
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.utils.getColorScheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getHolidaySchemeForCountry
import com.asivers.mycalendar.utils.getMonthViewBackgroundGradient
import com.asivers.mycalendar.utils.getSavedCountry
import com.asivers.mycalendar.utils.getSavedLocale
import com.asivers.mycalendar.utils.getSavedSettings
import com.asivers.mycalendar.utils.getSavedTheme
import com.asivers.mycalendar.utils.getSavedWeekendMode
import com.asivers.mycalendar.utils.getSizeScheme
import com.asivers.mycalendar.utils.getTranslationSchemeForExistingLocale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val ctx = LocalContext.current
            val systemLocale = LocalConfiguration.current.locales[0]
            val savedSettings = getSavedSettings(ctx)

            val savedCountry = getSavedCountry(savedSettings)
            val savedLocale = getSavedLocale(savedSettings, systemLocale, ctx)
            val savedTheme = getSavedTheme(savedSettings)
            val savedWeekendMode = getSavedWeekendMode(savedSettings)

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val selectedCountry = remember { mutableStateOf(savedCountry) }
                val selectedLocale = remember { mutableStateOf(savedLocale) }
                val selectedTheme = remember { mutableStateOf(savedTheme) }
                val selectedWeekendMode = remember { mutableStateOf(savedWeekendMode) }

                val selectedYear = remember { mutableIntStateOf(getCurrentYear()) }
                val selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) }
                val viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.MONTH)) }
                val lastSelectedYearFromMonthView = remember { mutableIntStateOf(getCurrentYear()) }

                val countryHolidayScheme = getHolidaySchemeForCountry(
                    selectedCountry.value, applicationContext)
                val translationScheme = getTranslationSchemeForExistingLocale(
                    selectedLocale.value, applicationContext)
                val colorScheme = getColorScheme(selectedTheme.value, selectedMonthIndex.intValue)

                val sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)

                val schemes = SchemeContainer(
                    countryHoliday = countryHolidayScheme,
                    translation = translationScheme,
                    color = colorScheme,
                    size = sizeScheme
                )

                if (viewShownInfo.value.current == ViewShown.SETTINGS) {
                    SettingsView(
                        modifier = Modifier.padding(innerPadding),
                        viewShownInfo = viewShownInfo,
                        selectedCountry = selectedCountry,
                        selectedLocale = selectedLocale,
                        selectedTheme = selectedTheme,
                        selectedWeekendMode = selectedWeekendMode,
                        schemes = schemes
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(getMonthViewBackgroundGradient(schemes.color))
                    ) {
                        if (viewShownInfo.value.current == ViewShown.MONTH) {
                            MonthView(
                                selectedYear = selectedYear,
                                selectedMonthIndex = selectedMonthIndex,
                                viewShownInfo = viewShownInfo,
                                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                                weekendMode = selectedWeekendMode.value,
                                schemes = schemes
                            )
                        } else {
                            YearView(
                                selectedYear = selectedYear,
                                selectedMonthIndex = selectedMonthIndex,
                                viewShownInfo = viewShownInfo,
                                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                                weekendMode = selectedWeekendMode.value,
                                schemes = schemes
                            )
                        }
                    }
                }
            }
        }
    }
}
