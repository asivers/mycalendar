package com.asivers.mycalendar.views.month

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTH_NAMES_LIST
import com.asivers.mycalendar.ui.theme.custom.CustomColor
import com.asivers.mycalendar.ui.theme.custom.CustomFont
import com.asivers.mycalendar.utils.getCurrentMonthIndex

@Preview(showBackground = true)
@Composable
fun SelectMonthDropdownPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomColor.MV_GRADIENT_TOP)
    ) {
        SelectMonthDropdown(
            modifier = Modifier,
            selectedMonthIndex = remember { mutableIntStateOf(getCurrentMonthIndex()) }
        )
    }
}

@Composable
fun SelectMonthDropdown(
    modifier: Modifier,
    selectedMonthIndex: MutableIntState
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .background(CustomColor.TRANSPARENT)
            .clickable(interactionSource = null, indication = null) {
                isExpanded.value = true
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconId = if (isExpanded.value) R.drawable.white_arrow_up else R.drawable.white_arrow_down
        Image(
            painter = painterResource(id = iconId),
            contentDescription = "DropDown Icon"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box {
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                text = MONTH_NAMES_LIST[selectedMonthIndex.intValue],
                color = CustomColor.WHITE,
                fontFamily = CustomFont.MONTSERRAT_BOLD,
                fontSize = 26.sp
            )
            SelectMonthDropdownList(
                isExpanded = isExpanded,
                selectedMonthIndex = selectedMonthIndex
            )
        }
    }
}

@Composable
fun SelectMonthDropdownList(
    isExpanded: MutableState<Boolean>,
    selectedMonthIndex: MutableIntState
) {
    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        }
    ) {
        val screenHeightDp = LocalConfiguration.current.screenHeightDp
        val itemHeightDp = (screenHeightDp - 32) / 17
        MONTH_NAMES_LIST.forEachIndexed { index, monthName ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = monthName,
                        color = CustomColor.MV_GRADIENT_BOTTOM,
                        fontFamily = CustomFont.MONTSERRAT_BOLD,
                        fontSize = 20.sp
                    )
                },
                onClick = {
                    isExpanded.value = false
                    selectedMonthIndex.intValue = index
                },
                modifier = Modifier.height(itemHeightDp.dp)
            )
        }
    }
}
