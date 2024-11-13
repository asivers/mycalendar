package com.example.mycalendar.views.month

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mycalendar.ui.theme.CustomColor
import com.example.mycalendar.utils.getCurrentYear

val yearsToSelect = List(201) { 1900 + it }

@Preview(showBackground = true)
@Composable
fun SelectYearDropdownPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomColor.Mv_gradient_top)
    ) {
        SelectYearDropdown(modifier = Modifier)
    }
}

@Composable
fun SelectYearDropdown(
    modifier: Modifier
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    val selectedYearIndex = remember {
        mutableIntStateOf(getCurrentYear() - 1900)
    }
    Box(
        modifier = modifier
    ) {
        SelectYearDropdownHeader(
            isExpanded = isExpanded,
            selectedYearIndex = selectedYearIndex,
        )
        SelectYearDropdownList(
            isExpanded = isExpanded,
            selectedYearIndex = selectedYearIndex,
        )
    }
}

@Composable
fun SelectYearDropdownHeader(
    isExpanded: MutableState<Boolean>,
    selectedYearIndex: MutableIntState
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomColor.Transparent)
            .clickable {
                isExpanded.value = true
            }
    ) {
        Text(
            text = yearsToSelect[selectedYearIndex.intValue].toString(),
            color = CustomColor.White,
            fontSize = 26.sp
        )
//            Image(
//                painter = painterResource(id = R.drawable.drop_down_ic),
//                contentDescription = "DropDown Icon"
//            )
    }
}

@Composable
fun SelectYearDropdownList(
    isExpanded: MutableState<Boolean>,
    selectedYearIndex: MutableIntState
) {
    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        }
    ) {
        yearsToSelect.forEachIndexed { index, year ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = year.toString(),
                        color = CustomColor.Myv_green_day_holiday
                    )
                },
                onClick = {
                    isExpanded.value = false
                    selectedYearIndex.intValue = index
                }
            )
        }
    }
}
