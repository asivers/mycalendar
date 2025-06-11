package com.asivers.mycalendar.composable.settings

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.utils.animateHeaderBackButtonOnViewChange
import com.asivers.mycalendar.utils.animateHeaderGearOnViewChange
import com.asivers.mycalendar.utils.noRippleClickable

@Composable
fun SettingsHeader(
    modifier: Modifier = Modifier,
    viewShownState: MutableState<ViewShownInfo>,
    onClickBack: () -> Unit,
    onGoToSettings: () -> Unit,
    schemes: SchemeContainer
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(18.dp, 20.dp, 18.dp, 0.dp)
    ) {
        AnimatedContent(
            targetState = viewShownState.value,
            transitionSpec = { animateHeaderBackButtonOnViewChange(targetState, initialState) },
            label = "settings header back icon animated content"
        ) {
            if (it.current != ViewShown.MONTH) {
                Image(
                    modifier = modifier.noRippleClickable { onClickBack() },
                    painter = painterResource(id = R.drawable.arrow_back),
                    colorFilter = ColorFilter.tint(schemes.color.brightElement),
                    contentDescription = "Go back icon"
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        AnimatedContent(
            targetState = viewShownState.value,
            transitionSpec = { animateHeaderGearOnViewChange(targetState, initialState) },
            label = "settings header settings icon animated content"
        ) {
            val onSettingsView = it.current == ViewShown.SETTINGS
            Image(
                modifier = modifier.noRippleClickable {
                    if (onSettingsView) onClickBack() else onGoToSettings()
                },
                painter = painterResource(id = R.drawable.settings_gear),
                colorFilter = ColorFilter.tint(
                    if (onSettingsView)
                        schemes.color.settingsGearOnSettingsView else schemes.color.brightElement
                ),
                contentDescription = "Settings icon"
            )
        }
    }
}
