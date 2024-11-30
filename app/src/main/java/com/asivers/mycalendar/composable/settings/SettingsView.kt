package com.asivers.mycalendar.composable.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.getSettingViewBackgroundGradient

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
        Spacer(modifier = Modifier.weight(1f))
    }
}
