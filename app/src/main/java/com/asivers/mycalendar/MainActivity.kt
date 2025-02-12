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
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.utils.animateContentOnViewChange
import com.asivers.mycalendar.utils.animateHeaderOnViewChange
import com.asivers.mycalendar.utils.backToPreviousView
import com.asivers.mycalendar.utils.changeView
import com.asivers.mycalendar.utils.getBackgroundGradient
import com.asivers.mycalendar.utils.getColorSchemeByMonthIndex
import com.asivers.mycalendar.utils.getHolidaySchemeForCountry
import com.asivers.mycalendar.utils.getOnDaySelectedCallback
import com.asivers.mycalendar.utils.proto.getSavedCountry
import com.asivers.mycalendar.utils.proto.getSavedLocale
import com.asivers.mycalendar.utils.proto.getSavedSettings
import com.asivers.mycalendar.utils.proto.getSavedTheme
import com.asivers.mycalendar.utils.proto.getSavedWeekendMode
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

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val selectedCountry = remember { mutableStateOf(savedCountry) }
                val selectedLocale = remember { mutableStateOf(savedLocale) }
                val selectedTheme = remember { mutableStateOf(savedTheme) }
                val selectedWeekendMode = remember { mutableStateOf(savedWeekendMode) }

                val selectedDateState = remember { mutableStateOf(SelectedDateInfo()) }
                val viewShownState = remember { mutableStateOf(ViewShownInfo(ViewShown.MONTH)) }

                val countryHolidayScheme = getHolidaySchemeForCountry(
                    selectedCountry.value, applicationContext)
                val translationScheme = getTranslationSchemeForExistingLocale(
                    selectedLocale.value, applicationContext)
                val colorScheme = selectedTheme.value.colorScheme
                    ?: getColorSchemeByMonthIndex(selectedDateState.value.monthIndex)

                val sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)

                val schemes = SchemeContainer(
                    countryHoliday = countryHolidayScheme,
                    translation = translationScheme,
                    color = colorScheme,
                    size = sizeScheme
                )

                BackHandler {
                    when (viewShownState.value.current) {
                        ViewShown.MONTH -> (ctx as? Activity)?.finish()
                        ViewShown.YEAR, ViewShown.DAY -> changeView(viewShownState, ViewShown.MONTH)
                        ViewShown.SETTINGS -> backToPreviousView(viewShownState)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding)
                        .imePadding()
                        .background(getBackgroundGradient(viewShownState.value.current, schemes.color))
                ) {
                    AnimatedContent(
                        targetState = viewShownState.value,
                        transitionSpec = { animateHeaderOnViewChange(targetState, initialState) },
                        label = "settings header animated content"
                    ) {
                        SettingsHeader(
                            viewShown = it.current,
                            schemes = schemes
                        ) {
                            if (it.current == ViewShown.SETTINGS)
                                backToPreviousView(viewShownState)
                            else
                                changeView(viewShownState, ViewShown.SETTINGS)
                        }
                    }
                    when (viewShownState.value.current) {
                        ViewShown.SETTINGS -> SettingsView(
                            selectedCountry = selectedCountry,
                            selectedLocale = selectedLocale,
                            selectedTheme = selectedTheme,
                            selectedWeekendMode = selectedWeekendMode,
                            schemes = schemes
                        )

                        ViewShown.YEAR -> YearView(
                            viewShownState = viewShownState,
                            selectedDateState = selectedDateState,
                            weekendMode = selectedWeekendMode.value,
                            schemes = schemes
                        )
                        ViewShown.MONTH, ViewShown.DAY -> {
                            SharedTransitionLayout {
                                AnimatedContent(
                                    targetState = viewShownState.value,
                                    transitionSpec = {
                                        animateContentOnViewChange(targetState, initialState)
                                    },
                                    label = "changing views animated content"
                                ) {
                                    if (it.current == ViewShown.MONTH)
                                        MonthView(
                                            selectedDateState = selectedDateState,
                                            onYearViewButtonClick = {
                                                changeView(viewShownState, ViewShown.YEAR)
                                            },
                                            onDaySelected = getOnDaySelectedCallback(
                                                viewShownState,
                                                selectedDateState
                                            ),
                                            animatedVisibilityScope = this@AnimatedContent,
                                            sharedTransitionScope = this@SharedTransitionLayout,
                                            weekendMode = selectedWeekendMode.value,
                                            schemes = schemes
                                        )
                                    else
                                        DayView(
                                            selectedDateState = selectedDateState,
                                            animatedVisibilityScope = this@AnimatedContent,
                                            sharedTransitionScope = this@SharedTransitionLayout,
                                            locale = selectedLocale.value,
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
}
