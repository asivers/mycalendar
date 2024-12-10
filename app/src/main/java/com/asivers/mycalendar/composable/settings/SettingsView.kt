package com.asivers.mycalendar.composable.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.SettingsParam
import com.asivers.mycalendar.enums.UserTheme
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.PreviewFrameWithSettingsHeader
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.getSettingViewBackgroundGradient
import kotlin.enums.enumEntries

@Preview(showBackground = true)
@Composable
fun SettingsViewPreview() {
    val schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    PreviewFrameWithSettingsHeader(
        viewShown = ViewShown.SETTINGS,
        getBackground = { getSettingViewBackgroundGradient(it) },
        schemes = schemes
    ) {
        SettingsView(
            selectedCountry = remember { mutableStateOf(Country.RUSSIA) },
            selectedLocale = remember { mutableStateOf(ExistingLocale.RU) },
            selectedTheme = remember { mutableStateOf(UserTheme.THEME_SUMMER) },
            selectedWeekendMode = remember { mutableStateOf(WeekendMode.SATURDAY_SUNDAY) },
            schemes = schemes
        )
    }
}

@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    selectedCountry: MutableState<Country>,
    selectedLocale: MutableState<ExistingLocale>,
    selectedTheme: MutableState<UserTheme>,
    selectedWeekendMode: MutableState<WeekendMode>,
    schemes: SchemeContainer
) {
    // todo adapt for different fonts
    val indentFromHeaderDp = getIndentFromHeaderDp(LocalConfiguration.current.screenHeightDp) + 1
    val expanded: MutableState<SettingsParam?> = remember { mutableStateOf(null) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(18.dp, indentFromHeaderDp.dp, 18.dp, 0.dp)
    ) {
        SettingsDropdown(
            expanded = expanded,
            selectedItem = selectedCountry,
            settingsParam = SettingsParam.COUNTRY,
            allItems = enumEntries<Country>(),
            maxItemsDisplayed = 14,
            schemes = schemes
        )
        Spacer(modifier = Modifier.height(32.dp))
        SettingsDropdown(
            expanded = expanded,
            selectedItem = selectedLocale,
            settingsParam = SettingsParam.EXISTING_LOCALE,
            allItems = enumEntries<ExistingLocale>(),
            maxItemsDisplayed = 11, // todo measure
            schemes = schemes
        )
        Spacer(modifier = Modifier.height(32.dp))
        SettingsDropdown(
            expanded = expanded,
            selectedItem = selectedTheme,
            settingsParam = SettingsParam.USER_THEME,
            allItems = enumEntries<UserTheme>(),
            maxItemsDisplayed = 6,
            schemes = schemes
        )
        Spacer(modifier = Modifier.height(32.dp))
        SettingsDropdown(
            expanded = expanded,
            selectedItem = selectedWeekendMode,
            settingsParam = SettingsParam.WEEKEND_MODE,
            allItems = enumEntries<WeekendMode>(),
            maxItemsDisplayed = 3,
            schemes = schemes
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
