package com.asivers.mycalendar.composable.dropdown

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTSERRAT
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.noRippleClickable

@Preview(showBackground = true)
@Composable
fun SelectYearDropdownPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SUMMER.monthViewTop)
    ) {
        SelectYearDropdown(
            selectedYearInfo = remember { mutableStateOf(SelectedYearInfo(getCurrentYear())) },
            selectedMonthInfo = SelectedMonthInfo(getCurrentYear(), getCurrentMonthIndex()),
            showYearView = false,
            schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
        )
    }
}

@Composable
fun SelectYearDropdown(
    modifier: Modifier = Modifier,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: SelectedMonthInfo,
    showYearView: Boolean,
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
            contentDescription = "DropDown Icon"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(modifier = Modifier.width(schemes.size.horizontal.yearDropdown)) {
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                text = selectedYearInfo.value.year.toString(),
                color = Color.White,
                fontFamily = MONTSERRAT_MEDIUM,
                fontSize = schemes.size.font.dropdownHeader
            )
            SelectYearDropdownList(
                isExpanded = isExpanded,
                selectedYearInfo = selectedYearInfo,
                selectedMonthInfo = selectedMonthInfo,
                showYearView = showYearView,
                schemes = schemes
            )
        }
    }
}

@Composable
fun SelectYearDropdownList(
    modifier: Modifier = Modifier,
    isExpanded: MutableState<Boolean>,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: SelectedMonthInfo,
    showYearView: Boolean,
    schemes: SchemeContainer
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val itemHeightDp = (screenHeightDp - 32) / 18
    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        },
        modifier = modifier,
        offset = DpOffset(x = (-5).dp, y = (-itemHeightDp - 5).dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .height((itemHeightDp * 14).dp)
                .width(schemes.size.horizontal.yearDropdown.plus(5.dp)),
            state = LazyListState(getYearIndex(selectedYearInfo.value.year))
        ) {
            items(201) { yearIndex ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = getYear(yearIndex).toString(),
                            modifier = Modifier.fillMaxWidth(),
                            color = if (selectedYearInfo.value.year == getYear(yearIndex))
                                schemes.color.viewsBottom else schemes.color.yearViewBtnTop,
                            fontFamily = if (selectedYearInfo.value.year == getYear(yearIndex))
                                MONTSERRAT_MEDIUM else MONTSERRAT,
                            fontSize = schemes.size.font.main,
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        isExpanded.value = false
                        val selectedYearVal = getYear(yearIndex)
                        selectedYearInfo.value = SelectedYearInfo(
                            year = getYear(yearIndex),
                            byDropdown = true
                        )
                        if (!showYearView) {
                            selectedMonthInfo.year = selectedYearVal
                        }
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
