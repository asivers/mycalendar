package com.asivers.mycalendar.composable.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dialog.PrivacyPolicyDialog
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.enums.ExistingLocale
import com.asivers.mycalendar.enums.NotificationsMode
import com.asivers.mycalendar.enums.SettingsParam
import com.asivers.mycalendar.enums.UserTheme
import com.asivers.mycalendar.enums.WeekNumbersMode
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.noRippleClickable
import com.asivers.mycalendar.utils.proto.getSavedNotificationsMode
import com.asivers.mycalendar.utils.proto.getSavedSettings
import kotlin.enums.enumEntries

@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    selectedCountry: MutableState<Country>,
    selectedLocale: MutableState<ExistingLocale>,
    selectedTheme: MutableState<UserTheme>,
    selectedWeekendMode: MutableState<WeekendMode>,
    selectedWeekNumbersMode: MutableState<WeekNumbersMode>,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val density = LocalDensity.current
    val indentFromHeaderDp = getIndentFromHeaderDp(ctx, density) + 1

    val selectedNotificationsMode: MutableState<NotificationsMode> = remember {
        mutableStateOf(getSavedNotificationsMode(getSavedSettings(ctx)))
    }

    val expanded: MutableState<SettingsParam?> = remember { mutableStateOf(null) }
    val privacyPolicyDialogShown = remember { mutableStateOf(false) }

    val spacerModifier = Modifier.height(schemes.size.vertical.betweenSettingsPadding)
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
            maxItemsDisplayed = 12,
            schemes = schemes
        )
        Spacer(modifier = spacerModifier)
        SettingsDropdown(
            expanded = expanded,
            selectedItem = selectedLocale,
            settingsParam = SettingsParam.EXISTING_LOCALE,
            allItems = enumEntries<ExistingLocale>(),
            maxItemsDisplayed = 9,
            schemes = schemes
        )
        Spacer(modifier = spacerModifier)
        SettingsDropdown(
            expanded = expanded,
            selectedItem = selectedTheme,
            settingsParam = SettingsParam.USER_THEME,
            allItems = enumEntries<UserTheme>(),
            maxItemsDisplayed = 7,
            schemes = schemes
        )
        Spacer(modifier = spacerModifier)
        SettingsDropdown(
            expanded = expanded,
            selectedItem = selectedWeekendMode,
            settingsParam = SettingsParam.WEEKEND_MODE,
            allItems = enumEntries<WeekendMode>(),
            maxItemsDisplayed = 3,
            schemes = schemes
        )
        Spacer(modifier = spacerModifier)
        SettingsDropdown(
            expanded = expanded,
            selectedItem = selectedWeekNumbersMode,
            settingsParam = SettingsParam.WEEK_NUMBERS_MODE,
            allItems = enumEntries<WeekNumbersMode>(),
            maxItemsDisplayed = 2,
            schemes = schemes
        )
        Spacer(modifier = spacerModifier)
        SettingsDropdown(
            expanded = expanded,
            selectedItem = selectedNotificationsMode,
            settingsParam = SettingsParam.NOTIFICATIONS_MODE,
            allItems = enumEntries<NotificationsMode>(),
            maxItemsDisplayed = 2,
            schemes = schemes
        )
        Spacer(modifier = spacerModifier)
        Text(
            text = schemes.translation.privacyPolicyLabel,
            modifier = Modifier
                .padding(24.dp, 0.dp, 0.dp, 0.dp)
                .alpha(if (expanded.value == null) 1f else 0.5f)
                .noRippleClickable { privacyPolicyDialogShown.value = true },
            color = schemes.color.text,
            fontFamily = MONTSERRAT_BOLD,
            fontSize = schemes.size.font.dropdownItem
        )
        Spacer(modifier = Modifier.weight(1f))
        if (privacyPolicyDialogShown.value) {
            PrivacyPolicyDialog(
                onCloseDialog = { privacyPolicyDialogShown.value = false },
                schemes = schemes
            )
        }
    }
}
