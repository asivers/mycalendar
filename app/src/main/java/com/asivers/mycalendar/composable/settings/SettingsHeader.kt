package com.asivers.mycalendar.composable.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.utils.noRippleClickable

@Composable
fun SettingsHeader(
    modifier: Modifier = Modifier,
    viewShown: ViewShown,
    schemes: SchemeContainer,
    onToggle: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(18.dp, 20.dp, 18.dp, 0.dp)
    ) {
        if (viewShown == ViewShown.SETTINGS) {
            Image(
                painter = painterResource(id = R.drawable.settings_gear),
                colorFilter = ColorFilter.tint(schemes.color.inactiveSettingsGear),
                contentDescription = "Inactive settings icon"
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.noRippleClickable { onToggle() },
                painter = painterResource(id = R.drawable.arrow_back),
                colorFilter = ColorFilter.tint(schemes.color.brightElement),
                contentDescription = "Go back icon"
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.noRippleClickable { onToggle() },
                painter = painterResource(id = R.drawable.settings_gear),
                colorFilter = ColorFilter.tint(schemes.color.brightElement),
                contentDescription = "Settings icon"
            )
        }
    }
}
