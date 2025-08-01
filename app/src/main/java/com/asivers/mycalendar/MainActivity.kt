package com.asivers.mycalendar

import android.os.Bundle
import android.os.Vibrator
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.core.content.ContextCompat
import com.asivers.mycalendar.composable.day.DayView
import com.asivers.mycalendar.composable.dialog.PermissionRevokedDialog
import com.asivers.mycalendar.composable.month.MonthView
import com.asivers.mycalendar.composable.settings.SettingsHeader
import com.asivers.mycalendar.composable.settings.SettingsView
import com.asivers.mycalendar.composable.year.YearView
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.receivers.JUMP_TO_DATE
import com.asivers.mycalendar.utils.animateContentOnViewChange
import com.asivers.mycalendar.utils.backToPreviousView
import com.asivers.mycalendar.utils.changeView
import com.asivers.mycalendar.utils.getBackgroundGradient
import com.asivers.mycalendar.utils.getColorSchemeByMonthValue
import com.asivers.mycalendar.utils.getHolidaySchemeForCountry
import com.asivers.mycalendar.utils.getOnDaySelectedCallback
import com.asivers.mycalendar.utils.getSizeScheme
import com.asivers.mycalendar.utils.getTranslationSchemeForExistingLocale
import com.asivers.mycalendar.utils.makeNavigationBarBlack
import com.asivers.mycalendar.utils.notification.createNotificationChannels
import com.asivers.mycalendar.utils.notification.getPermissionTypeToShowWarningRevoked
import com.asivers.mycalendar.utils.notification.registerNotificationPermissionRequestLauncher
import com.asivers.mycalendar.utils.paddingNavBarAdjusted
import com.asivers.mycalendar.utils.proto.cleanupAllNotificationsInPast
import com.asivers.mycalendar.utils.proto.getInfoAboutAllNotificationsInPast
import com.asivers.mycalendar.utils.proto.getSavedCountry
import com.asivers.mycalendar.utils.proto.getSavedLocale
import com.asivers.mycalendar.utils.proto.getSavedSettings
import com.asivers.mycalendar.utils.proto.getSavedTheme
import com.asivers.mycalendar.utils.proto.getSavedWeekNumbersMode
import com.asivers.mycalendar.utils.proto.getSavedWeekendMode
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerNotificationPermissionRequestLauncher(this)
        enableEdgeToEdge()
        makeNavigationBarBlack(window)
        setContent {
            ContextCompat.getSystemService(this, Vibrator::class.java)?.cancel()
            createNotificationChannels(this)

            val systemLocale = LocalConfiguration.current.locales[0]
            val savedSettings = getSavedSettings(this)

            val savedCountry = getSavedCountry(savedSettings, systemLocale, this)
            val savedLocale = getSavedLocale(savedSettings, systemLocale, this)
            val savedTheme = getSavedTheme(savedSettings)
            val savedWeekendMode = getSavedWeekendMode(savedSettings)
            val savedWeekNumbersMode = getSavedWeekNumbersMode(savedSettings)

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val selectedCountry = remember { mutableStateOf(savedCountry) }
                val selectedLocale = remember { mutableStateOf(savedLocale) }
                val selectedTheme = remember { mutableStateOf(savedTheme) }
                val selectedWeekendMode = remember { mutableStateOf(savedWeekendMode) }
                val selectedWeekNumbersMode = remember { mutableStateOf(savedWeekNumbersMode) }

                val jumpToDate = intent.getStringExtra(JUMP_TO_DATE)
                val selectedDateState = remember {
                    mutableStateOf(
                        if (jumpToDate != null)
                            SelectedDateInfo(jumpToDate) else SelectedDateInfo(LocalDate.now())
                    )
                }
                val viewShownState = remember {
                    mutableStateOf(
                        ViewShownInfo(
                            if (jumpToDate != null) ViewShown.DAY else ViewShown.MONTH)
                    )
                }

                val countryHolidayScheme = getHolidaySchemeForCountry(
                    selectedCountry.value, applicationContext)
                val translationScheme = getTranslationSchemeForExistingLocale(
                    selectedLocale.value, applicationContext)
                val colorScheme = selectedTheme.value.colorScheme
                    ?: getColorSchemeByMonthValue(selectedDateState.value.monthValue)

                val sizeScheme = getSizeScheme(LocalConfiguration.current, LocalDensity.current)

                val schemes = SchemeContainer(
                    countryHoliday = countryHolidayScheme,
                    translation = translationScheme,
                    color = colorScheme,
                    size = sizeScheme
                )

                BackHandler {
                    when (viewShownState.value.current) {
                        ViewShown.MONTH -> finish()
                        ViewShown.YEAR, ViewShown.DAY -> changeView(viewShownState, ViewShown.MONTH)
                        ViewShown.SETTINGS -> backToPreviousView(viewShownState)
                    }
                }

                Column(
                    modifier = Modifier
                        .background(
                            getBackgroundGradient(viewShownState.value.current, schemes.color)
                        )
                        .paddingNavBarAdjusted(innerPadding)
                        .consumeWindowInsets(innerPadding)
                        .imePadding()
                ) {
                    SettingsHeader(
                        viewShownState = viewShownState,
                        onClickBack = {
                            when (viewShownState.value.current) {
                                ViewShown.MONTH -> {} // will never happen
                                ViewShown.YEAR -> changeView(viewShownState, ViewShown.MONTH)
                                ViewShown.DAY -> onBackPressedDispatcher.onBackPressed()
                                ViewShown.SETTINGS -> backToPreviousView(viewShownState)
                            }
                        },
                        onGoToSettings = { changeView(viewShownState, ViewShown.SETTINGS) },
                        schemes = schemes
                    )
                    when (viewShownState.value.current) {
                        ViewShown.SETTINGS -> SettingsView(
                            selectedCountry = selectedCountry,
                            selectedLocale = selectedLocale,
                            selectedTheme = selectedTheme,
                            selectedWeekendMode = selectedWeekendMode,
                            selectedWeekNumbersMode = selectedWeekNumbersMode,
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
                                            weekNumbersMode = selectedWeekNumbersMode.value,
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

                val permissionTypeToShowWarningRevoked = remember {
                    mutableStateOf(getPermissionTypeToShowWarningRevoked(this))
                }
                if (permissionTypeToShowWarningRevoked.value != null) {
                    PermissionRevokedDialog(
                        onCloseDialog = {
                            val notificationsInPast = getInfoAboutAllNotificationsInPast(this)
                            cleanupAllNotificationsInPast(this, notificationsInPast)
                            permissionTypeToShowWarningRevoked.value = null
                        },
                        permissionType = permissionTypeToShowWarningRevoked.value,
                        schemes = schemes
                    )
                }
            }
        }
    }
}
