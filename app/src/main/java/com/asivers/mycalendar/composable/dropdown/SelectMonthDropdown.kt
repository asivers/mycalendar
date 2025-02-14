package com.asivers.mycalendar.composable.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.utils.noRippleClickable

@Composable
fun SelectMonthDropdown(
    modifier: Modifier = Modifier,
    onMonthSelected: (Int) -> Unit,
    thisMonthValue: Int,
    schemes: SchemeContainer
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .background(Color.Transparent)
            .noRippleClickable { isExpanded.value = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconId = if (isExpanded.value) R.drawable.white_arrow_up else R.drawable.white_arrow_down
        Image(
            painter = painterResource(id = iconId),
            colorFilter = ColorFilter.tint(schemes.color.text),
            contentDescription = "DropDown Icon"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box {
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                text = schemes.translation.months[thisMonthValue - 1],
                color = schemes.color.text,
                fontFamily = MONTSERRAT_BOLD,
                fontSize = schemes.size.font.dropdownHeader
            )
            SelectMonthDropdownList(
                isExpanded = isExpanded,
                onMonthSelected = onMonthSelected,
                schemes = schemes
            )
        }
    }
}

@Composable
fun SelectMonthDropdownList(
    modifier: Modifier = Modifier,
    isExpanded: MutableState<Boolean>,
    onMonthSelected: (Int) -> Unit,
    schemes: SchemeContainer
) {
    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        },
        modifier = modifier.background(schemes.color.dropdownBackground)
    ) {
        val screenHeightDp = LocalConfiguration.current.screenHeightDp
        val itemHeightDp = (screenHeightDp - 32) / 17
        schemes.translation.months.forEachIndexed { index, monthName ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = monthName,
                        color = schemes.color.monthOrSelectedItemInDropdown,
                        fontFamily = MONTSERRAT_BOLD,
                        fontSize = schemes.size.font.dropdownItem
                    )
                },
                onClick = {
                    isExpanded.value = false
                    onMonthSelected(index)
                },
                modifier = Modifier.height(itemHeightDp.dp)
            )
        }
    }
}
