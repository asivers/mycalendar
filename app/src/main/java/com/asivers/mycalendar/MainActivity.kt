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
import com.asivers.mycalendar.composable.month.MonthView
import com.asivers.mycalendar.composable.settings.SettingsView
import com.asivers.mycalendar.composable.year.YearView
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.enums.UserTheme
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.getColorScheme
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getExistingLocaleForLanguage
import com.asivers.mycalendar.utils.getHolidaySchemeForCountry
import com.asivers.mycalendar.utils.getMonthViewBackgroundGradient
import com.asivers.mycalendar.utils.getSizeScheme
import com.asivers.mycalendar.utils.getTranslationSchemeForExistingLocale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val systemLanguage = LocalConfiguration.current.locales[0].language
                val existingLocaleForSystem = getExistingLocaleForLanguage(systemLanguage)

                val selectedCountry = remember { mutableStateOf(Country.SPAIN_REUS) }
                val selectedLocale = remember { mutableStateOf(existingLocaleForSystem) }
                val selectedTheme = remember { mutableStateOf(UserTheme.CHANGE_BY_SEASON) }
                val selectedWeekendMode = remember { mutableStateOf(WeekendMode.SATURDAY_SUNDAY) }

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
                                schemes = schemes
                            )
                        } else {
                            YearView(
                                selectedYear = selectedYear,
                                selectedMonthIndex = selectedMonthIndex,
                                viewShownInfo = viewShownInfo,
                                lastSelectedYearFromMonthView = lastSelectedYearFromMonthView,
                                schemes = schemes
                            )
                        }
                    }
                }
            }
        }
    }
}
