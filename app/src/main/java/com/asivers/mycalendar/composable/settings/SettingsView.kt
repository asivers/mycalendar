package com.asivers.mycalendar.composable.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.SettingsParam
import com.asivers.mycalendar.enums.UserTheme
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.getSettingViewBackgroundGradient
import kotlin.enums.enumEntries

@Preview(showBackground = true)
@Composable
fun SettingsViewPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        SettingsView(
            modifier = Modifier.padding(innerPadding),
            viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.SETTINGS, ViewShown.MONTH)) },
            schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
        )
    }
}

@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    viewShownInfo: MutableState<ViewShownInfo>,
    schemes: SchemeContainer
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = getSettingViewBackgroundGradient(schemes.color))
    ) {
        SettingsHeader(
            viewShownInfo = viewShownInfo,
            schemes = schemes
        )
        SettingsContent(
            modifier = Modifier.padding(25.dp, 5.dp),
            schemes = schemes
        )
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    schemes: SchemeContainer
) {
    val country = remember { mutableStateOf(Country.RUSSIA) }
    val locale = remember { mutableStateOf(ExistingLocale.RU) }
    val theme = remember { mutableStateOf(UserTheme.THEME_SUMMER) }
    val weekendMode = remember { mutableStateOf(WeekendMode.SATURDAY_SUNDAY) }

    val expanded: MutableState<SettingsParam?> = remember { mutableStateOf(null) }

    Column {
        if (expanded.value == null || expanded.value == SettingsParam.COUNTRY) {
            SettingsDropdown(
                modifier = modifier.padding(0.dp, 20.dp),
                settingsParam = SettingsParam.COUNTRY,
                allItems = enumEntries<Country>(),
                selectedItem = country,
                expanded = expanded,
                schemes = schemes
            )
        }
        if (expanded.value == null || expanded.value == SettingsParam.EXISTING_LOCALE) {
            SettingsDropdown(
                modifier = modifier.padding(0.dp, 20.dp),
                settingsParam = SettingsParam.EXISTING_LOCALE,
                allItems = enumEntries<ExistingLocale>(),
                selectedItem = locale,
                expanded = expanded,
                schemes = schemes
            )
        }
        if (expanded.value == null || expanded.value == SettingsParam.USER_THEME) {
            SettingsDropdown(
                modifier = modifier.padding(0.dp, 20.dp),
                settingsParam = SettingsParam.USER_THEME,
                allItems = enumEntries<UserTheme>(),
                selectedItem = theme,
                expanded = expanded,
                schemes = schemes
            )
        }
        if (expanded.value == null || expanded.value == SettingsParam.WEEKEND_MODE) {
            SettingsDropdown(
                modifier = modifier.padding(0.dp, 20.dp),
                settingsParam = SettingsParam.WEEKEND_MODE,
                allItems = enumEntries<WeekendMode>(),
                selectedItem = weekendMode,
                expanded = expanded,
                schemes = schemes
            )
        }
    }
}
