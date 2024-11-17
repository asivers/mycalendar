package com.asivers.mycalendar.views.month

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.ui.theme.custom.CustomColor
import com.asivers.mycalendar.ui.theme.custom.CustomFont
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.noRippleClickable

@Preview(showBackground = true)
@Composable
fun SelectYearDropdownPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomColor.MV_GRADIENT_TOP)
    ) {
        SelectYearDropdown(
            modifier = Modifier,
            selectedYear = remember { mutableIntStateOf(getCurrentYear()) }
        )
    }
}

@Composable
fun SelectYearDropdown(
    modifier: Modifier,
    selectedYear: MutableIntState
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .background(CustomColor.TRANSPARENT)
            .noRippleClickable { isExpanded.value = true },
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
                text = selectedYear.intValue.toString(),
                color = CustomColor.WHITE,
                fontFamily = CustomFont.MONTSERRAT_MEDIUM,
                fontSize = 26.sp
            )
            SelectYearDropdownList(
                isExpanded = isExpanded,
                selectedYear = selectedYear,
            )
        }
    }
}

@Composable
fun SelectYearDropdownList(
    isExpanded: MutableState<Boolean>,
    selectedYear: MutableIntState
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val itemHeightDp = (screenHeightDp - 32) / 18
    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        },
        offset = DpOffset(x = (-5).dp, y = (-itemHeightDp - 5).dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .height((itemHeightDp * 14).dp)
                .width(75.dp),
            state = LazyListState(getYearIndex(selectedYear.intValue))
        ) {
            items(201) { yearIndex ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = getYear(yearIndex).toString(),
                            modifier = Modifier.fillMaxWidth(),
                            color = if (selectedYear.intValue == getYear(yearIndex))
                                CustomColor.MV_GRADIENT_BOTTOM else CustomColor.MYV_GREEN_DAY_HOLIDAY,
                            fontFamily = if (selectedYear.intValue == getYear(yearIndex))
                                CustomFont.MONTSERRAT_MEDIUM else CustomFont.MONTSERRAT,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        isExpanded.value = false
                        selectedYear.intValue = getYear(yearIndex)
                    },
                    modifier = Modifier.height(itemHeightDp.dp),
                    contentPadding = PaddingValues(0.dp)
                )
            }
        }
    }
}

private fun getYear(yearIndex: Int): Int = yearIndex + 1900
private fun getYearIndex(year: Int): Int = year - 1900
