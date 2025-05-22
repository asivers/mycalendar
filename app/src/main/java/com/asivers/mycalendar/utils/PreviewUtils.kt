package com.asivers.mycalendar.utils

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Density
import com.asivers.mycalendar.composable.settings.SettingsHeader
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.HolidayInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.scheme.ColorScheme
import com.asivers.mycalendar.data.scheme.CountryHolidayScheme
import com.asivers.mycalendar.data.scheme.TranslationScheme
import com.asivers.mycalendar.enums.ViewShown
import java.time.Month

fun getSchemesForPreview(config: Configuration, density: Density): SchemeContainer {
    return SchemeContainer(
        countryHoliday = getCountryHolidaySchemeForPreview(),
        translation = getTranslationSchemeForPreview(),
        color = SUMMER,
        size = getSizeScheme(config, density)
    )
}

fun getCountryHolidaySchemeForPreview(): CountryHolidayScheme {
    return CountryHolidayScheme(
        everyYear = mapOf(
            Month.JANUARY.value to mapOf(
                Pair(1, HolidayInfo(en = "Spain - New Year's Day", es = "España - Año Nuevo")),
                Pair(6, HolidayInfo(en = "Spain - Epiphany", es = "España - Epifanía del Señor"))
            ),
            Month.MAY.value to mapOf(
                Pair(1, HolidayInfo(en = "Spain - Labour Day", es = "España - Día del Trabajador"))
            ),
            Month.JUNE.value to mapOf(
                Pair(24, HolidayInfo(en = "Catalonia - John the Baptist", es = "Cataluña - Sant Joan"))
            ),
            Month.AUGUST.value to mapOf(
                Pair(15, HolidayInfo(en = "Spain - Assumption", es = "España - Asunción"))
            ),
            Month.SEPTEMBER.value to mapOf(
                Pair(11, HolidayInfo(en = "Catalonia - National Day of Catalonia", es = "Cataluña - Diada Nacional de Catalunya")),
                Pair(25, HolidayInfo(en = "Reus - Misericordia", es = "Reus - Misericòrdia"))
            ),
            Month.OCTOBER.value to mapOf(
                Pair(12, HolidayInfo(en = "Spain - National Day of Spain", es = "España - Fiesta Nacional de España"))
            ),
            Month.NOVEMBER.value to mapOf(
                Pair(1, HolidayInfo(en = "Spain - All Saints' Day", es = "España - Día de todos los Santos"))
            ),
            Month.DECEMBER.value to mapOf(
                Pair(6, HolidayInfo(en = "Spain - Constitution Day", es = "España - Dia de la Constitucion Espanola")),
                Pair(8, HolidayInfo(en = "Spain - Immaculate Conception", es = "España - Inmaculada Concepción")),
                Pair(25, HolidayInfo(en = "Spain - Christmas Day", es = "España - Navidad")),
                Pair(26, HolidayInfo(en = "Catalonia - St. Stephen's Day", es = "Cataluña - Sant Esteve"))
            )
        ),
        oneTime = mapOf(
            2024 to mapOf(
                Month.MARCH.value to mapOf(
                    Pair(29, HolidayInfo(en = "Spain - Good Friday", es = "España - Viernes Santo"))
                ),
                Month.APRIL.value to mapOf(
                    Pair(1, HolidayInfo(en = "Catalonia - Easter Monday", es = "Cataluña - Lunes de Pascua"))
                ),
                Month.JUNE.value to mapOf(
                    Pair(29, HolidayInfo(en = "Reus - Saint Peter", es = "Reus - Sant Pere"))
                )
            ),
            2025 to mapOf(
                Month.APRIL.value to mapOf(
                    Pair(18, HolidayInfo(en = "Spain - Good Friday", es = "España - Viernes Santo")),
                    Pair(21, HolidayInfo(en = "Catalonia - Easter Monday", es = "Cataluña - Lunes de Pascua"))
                ),
                Month.JUNE.value to mapOf(
                    Pair(30, HolidayInfo(en = "Reus - Saint Peter", es = "Reus - Sant Pere"))
                )
            )
        )
    )
}

fun getTranslationSchemeForPreview(): TranslationScheme {
    return TranslationScheme(
        yearView = "Year view",
        months = listOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        ),
        daysOfWeek3 = listOf(
            "Mon",
            "Tue",
            "Wed",
            "Thu",
            "Fri",
            "Sat",
            "Sun"
        ),
        daysOfWeek1 = listOf(
            "M",
            "T",
            "W",
            "T",
            "F",
            "S",
            "S"
        ),
        countriesParam = "Country",
        countries = mapOf(
            Pair("noDisplay", "Do not display"),
            Pair("russia", "Russia"),
            Pair("spain", "Spain")
        ),
        localesParam = "Language",
        locales = mapOf(
            Pair("en", "English"),
            Pair("ru", "Russian")
        ),
        themesParam = "Theme",
        themes = mapOf(
            Pair("changeBySeason", "Change by season"),
            Pair("summer", "Summer"),
            Pair("autumn", "Autumn"),
            Pair("winter", "Winter"),
            Pair("spring", "Spring")
        ),
        weekendModesParam = "Display weekends",
        weekendModes = mapOf(
            Pair("saturdaySunday", "Saturday & Sunday"),
            Pair("onlySunday", "Only Sunday"),
            Pair("noDisplay", "Do not display")
        ),
        weekNumbersModesParam = "Show week numbers",
        weekNumbersModes = mapOf(
            Pair("off", "Off"),
            Pair("on", "On")
        ),
        notificationsModesParam = "Notifications mode",
        notificationsModes = mapOf(
            Pair("withRingtone", "Push & ringtone"),
            Pair("withoutRingtone", "Just push")
        ),
        makeNote = "Make a note",
        saveNote = "Save",
        deleteNote = "Delete",
        switchEveryYear = "Every year",
        switchNotification = "Notification",
        dismiss = "Dismiss",
        confirm = "Confirm",
        ok = "OK",
        goToSettings = "Go to settings",
        requestAlarmPermission = "The app requests a permission to schedule alarms",
        explainNotificationPermissionDenied = "Permission to send notifications has been denied by the user. To use notifications, enable permission to send notifications in your device settings",
        explainNotificationPermissionRevoked = "Permission to send notifications has been revoked by the user. Existing notifications will not work. To use notifications, enable permission to send notifications in your device settings",
        explainAlarmPermissionRevoked = "Permission to schedule alarms has been revoked by the user. Existing notifications will not work. To use notifications, enable permission to send notifications in your device settings",
        alarmInPastToast = "It is not possible to set notification alarm in the past"
    )
}

@Composable
fun PreviewFrameWithSettingsHeader(
    viewShown: ViewShown,
    getBackground: (ColorScheme) -> Brush,
    schemes: SchemeContainer,
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(getBackground(schemes.color))
        ) {
            SettingsHeader(
                viewShown = viewShown,
                schemes = schemes,
                onToggle = {}
            )
            Box(
                content = content
            )
        }
    }
}
