package com.example.mycalendar.views.month

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalendar.R
import com.example.mycalendar.constants.MONTH_NAMES_LIST
import com.example.mycalendar.ui.theme.custom.CustomColor
import com.example.mycalendar.ui.theme.custom.CustomFont
import com.example.mycalendar.utils.getCurrentMonthIndex

@Preview(showBackground = true)
@Composable
fun SelectMonthDropdownPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomColor.MV_GRADIENT_TOP)
    ) {
        SelectMonthDropdown(modifier = Modifier)
    }
}

@Composable
fun SelectMonthDropdown(
    modifier: Modifier
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    val selectedMonthIndex = remember {
        mutableIntStateOf(getCurrentMonthIndex())
    }
    Box(
        modifier = modifier
    ) {
        SelectMonthDropdownHeader(
            isExpanded = isExpanded,
            selectedMonthIndex = selectedMonthIndex,
        )
        SelectMonthDropdownList(
            isExpanded = isExpanded,
            selectedMonthIndex = selectedMonthIndex,
        )
    }
}

@Composable
fun SelectMonthDropdownHeader(
    isExpanded: MutableState<Boolean>,
    selectedMonthIndex: MutableIntState
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentWidth()
            .background(CustomColor.TRANSPARENT)
            .clickable {
                isExpanded.value = true
            }
    ) {
        val iconId = if (isExpanded.value) R.drawable.white_arrow_up else R.drawable.white_arrow_down
        Image(
            painter = painterResource(id = iconId),
            contentDescription = "DropDown Icon"
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = MONTH_NAMES_LIST[selectedMonthIndex.intValue],
            color = CustomColor.WHITE,
            fontFamily = CustomFont.MONTSERRAT_BOLD,
            fontSize = 32.sp
        )
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
        MONTH_NAMES_LIST.forEachIndexed { index, monthName ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = monthName,
                        color = CustomColor.MV_GRADIENT_BOTTOM,
                        fontFamily = CustomFont.MONTSERRAT_BOLD,
                        fontSize = 22.sp
                    )
                },
                onClick = {
                    isExpanded.value = false
                    selectedMonthIndex.intValue = index
                }
            )
        }
    }
}
