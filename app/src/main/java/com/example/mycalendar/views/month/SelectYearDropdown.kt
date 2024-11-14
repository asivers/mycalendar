package com.example.mycalendar.views.month

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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.mycalendar.ui.theme.custom.CustomColor
import com.example.mycalendar.ui.theme.custom.CustomFont
import com.example.mycalendar.utils.getCurrentYear

fun getYearByIndex(yearIndex: Int): String = (1900 + yearIndex).toString()

@Preview(showBackground = true)
@Composable
fun SelectYearDropdownPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomColor.MV_GRADIENT_TOP)
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
    Row(
        modifier = modifier
            .background(CustomColor.TRANSPARENT)
            .clickable {
                isExpanded.value = true
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val iconId = if (isExpanded.value) R.drawable.white_arrow_up else R.drawable.white_arrow_down
        Image(
            painter = painterResource(id = iconId),
            contentDescription = "DropDown Icon"
        )
        Spacer(modifier = Modifier.width(5.dp))
        Box {
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                text = getYearByIndex(selectedYearIndex.intValue),
                color = CustomColor.WHITE,
                fontFamily = CustomFont.MONTSERRAT_MEDIUM,
                fontSize = 28.sp
            )
            SelectYearDropdownList(
                isExpanded = isExpanded,
                selectedYearIndex = selectedYearIndex,
            )
        }
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
        LazyColumn(
            modifier = Modifier
                .height(600.dp)
                .width(85.dp)
        ) {
            items(201) { yearIndex ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = getYearByIndex(yearIndex),
                            color = CustomColor.MYV_GREEN_DAY_HOLIDAY,
                            fontFamily = CustomFont.MONTSERRAT,
                            fontSize = 26.sp
                        )
                    },
                    onClick = {
                        isExpanded.value = false
                        selectedYearIndex.intValue = yearIndex
                    }
                )
            }
        }
    }
}
