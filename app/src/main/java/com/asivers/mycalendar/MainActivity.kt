package com.asivers.mycalendar

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import com.asivers.mycalendar.composable.day.DayView
import com.asivers.mycalendar.composable.month.MonthView
import com.asivers.mycalendar.composable.settings.SettingsHeader
import com.asivers.mycalendar.composable.settings.SettingsView
import com.asivers.mycalendar.composable.year.YearView
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.utils.animateContentOnViewChange
import com.asivers.mycalendar.utils.animateHeaderOnViewChange
import com.asivers.mycalendar.utils.backToPreviousView
import com.asivers.mycalendar.utils.changeView
import com.asivers.mycalendar.utils.getBackgroundGradient
import com.asivers.mycalendar.utils.getColorScheme
import com.asivers.mycalendar.utils.getCurrentDayOfMonth
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getHolidaySchemeForCountry
import com.asivers.mycalendar.utils.getMonthInfo
import com.asivers.mycalendar.utils.getOnDaySelectedCallback
import com.asivers.mycalendar.utils.getSavedCountry
import com.asivers.mycalendar.utils.getSavedLocale
import com.asivers.mycalendar.utils.getSavedSettings
import com.asivers.mycalendar.utils.getSavedTheme
import com.asivers.mycalendar.utils.getSavedWeekendMode
import com.asivers.mycalendar.utils.getSizeScheme
import com.asivers.mycalendar.utils.getTranslationSchemeForExistingLocale

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
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

            val currentYear = getCurrentYear()
            val currentMonthIndex = getCurrentMonthIndex()
            val currentDayOfMonth = getCurrentDayOfMonth()

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val selectedCountry = remember { mutableStateOf(savedCountry) }
                val selectedLocale = remember { mutableStateOf(savedLocale) }
                val selectedTheme = remember { mutableStateOf(savedTheme) }
                val selectedWeekendMode = remember { mutableStateOf(savedWeekendMode) }

                val selectedYearInfo = remember { mutableStateOf(SelectedYearInfo(currentYear)) }
                val selectedMonthInfo = remember { mutableStateOf(SelectedMonthInfo(currentYear, currentMonthIndex)) }
                val selectedDay = remember { mutableIntStateOf(currentDayOfMonth) }
                val viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.MONTH)) }

                val countryHolidayScheme = getHolidaySchemeForCountry(
                    selectedCountry.value, applicationContext)
                val translationScheme = getTranslationSchemeForExistingLocale(
                    selectedLocale.value, applicationContext)
                val colorScheme = getColorScheme(selectedTheme.value, selectedMonthInfo.value.monthIndex)

                val sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)

                val schemes = SchemeContainer(
                    countryHoliday = countryHolidayScheme,
                    translation = translationScheme,
                    color = colorScheme,
                    size = sizeScheme
                )

                BackHandler {
                    when (viewShownInfo.value.current) {
                        ViewShown.MONTH -> (ctx as? Activity)?.finish()
                        ViewShown.YEAR, ViewShown.DAY -> changeView(viewShownInfo, ViewShown.MONTH)
                        ViewShown.SETTINGS -> backToPreviousView(viewShownInfo)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .background(getBackgroundGradient(viewShownInfo.value.current, schemes.color))
                ) {
                    AnimatedContent(
                        targetState = viewShownInfo.value,
                        transitionSpec = { animateHeaderOnViewChange(targetState, initialState) },
                        label = "settings header animated content"
                    ) {
                        SettingsHeader(
                            viewShown = it.current,
                            schemes = schemes
                        ) {
                            if (it.current == ViewShown.SETTINGS)
                                backToPreviousView(viewShownInfo)
                            else
                                changeView(viewShownInfo, ViewShown.SETTINGS)
                        }
                    }
                    SharedTransitionLayout {
                        AnimatedContent(
                            targetState = viewShownInfo.value,
                            transitionSpec = { animateContentOnViewChange(targetState, initialState) },
                            label = "changing views animated content"
                        ) {
                            when (it.current) {
                                ViewShown.SETTINGS -> SettingsView(
                                    selectedCountry = selectedCountry,
                                    selectedLocale = selectedLocale,
                                    selectedTheme = selectedTheme,
                                    selectedWeekendMode = selectedWeekendMode,
                                    schemes = schemes
                                )
                                ViewShown.MONTH -> MonthView(
                                    selectedYearInfo = selectedYearInfo,
                                    selectedMonthInfo = selectedMonthInfo,
                                    viewShownInfo = viewShownInfo,
                                    onDaySelected = getOnDaySelectedCallback(selectedDay, viewShownInfo),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    weekendMode = selectedWeekendMode.value,
                                    schemes = schemes
                                )
                                ViewShown.YEAR -> YearView(
                                    selectedYearInfo = selectedYearInfo,
                                    selectedMonthInfo = selectedMonthInfo,
                                    viewShownInfo = viewShownInfo,
                                    weekendMode = selectedWeekendMode.value,
                                    schemes = schemes
                                )
                                ViewShown.DAY -> DayView(
                                    selectedYearInfo = selectedYearInfo,
                                    selectedMonthInfo = selectedMonthInfo,
                                    selectedDay = selectedDay,
                                    viewShownInfo = viewShownInfo,
                                    animatedVisibilityScope = this@AnimatedContent,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    thisMonthInfo = getMonthInfo(
                                        year = selectedMonthInfo.value.year,
                                        monthIndex = selectedMonthInfo.value.monthIndex,
                                        countryHolidayScheme = schemes.countryHoliday,
                                        forYearView = false
                                    ),
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
}
