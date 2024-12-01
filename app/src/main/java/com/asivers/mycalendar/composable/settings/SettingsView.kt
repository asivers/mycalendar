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
        SettingsDropdown(
            modifier = modifier.padding(0.dp, 20.dp),
            expanded = expanded,
            selectedItem = country,
            settingsParam = SettingsParam.COUNTRY,
            allItems = enumEntries<Country>(),
            maxItemsDisplayed = 14,
            schemes = schemes
        )
        SettingsDropdown(
            modifier = modifier.padding(0.dp, 20.dp),
            expanded = expanded,
            selectedItem = locale,
            settingsParam = SettingsParam.EXISTING_LOCALE,
            allItems = enumEntries<ExistingLocale>(),
            maxItemsDisplayed = 11, // todo measure
            schemes = schemes
        )
        SettingsDropdown(
            modifier = modifier.padding(0.dp, 20.dp),
            expanded = expanded,
            selectedItem = theme,
            settingsParam = SettingsParam.USER_THEME,
            allItems = enumEntries<UserTheme>(),
            maxItemsDisplayed = 6,
            schemes = schemes
        )
        SettingsDropdown(
            modifier = modifier.padding(0.dp, 20.dp),
            expanded = expanded,
            selectedItem = weekendMode,
            settingsParam = SettingsParam.WEEKEND_MODE,
            allItems = enumEntries<WeekendMode>(),
            maxItemsDisplayed = 3,
            schemes = schemes
        )
    }
}
