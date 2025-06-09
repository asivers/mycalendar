package com.asivers.mycalendar.composable.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTSERRAT
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.utils.getInsetsVerticalPaddingDp
import com.asivers.mycalendar.utils.getScreenHeightDp
import com.asivers.mycalendar.utils.noRippleClickable

@Composable
fun SelectYearDropdown(
    modifier: Modifier = Modifier,
    onYearSelected: (Int) -> Unit,
    thisYear: Int,
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
        Box(modifier = Modifier.width(schemes.size.horizontal.yearDropdown)) {
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                text = thisYear.toString(),
                color = schemes.color.text,
                fontFamily = MONTSERRAT_MEDIUM,
                fontSize = schemes.size.font.dropdownHeader
            )
            SelectYearDropdownList(
                isExpanded = isExpanded,
                onYearSelected = onYearSelected,
                thisYear = thisYear,
                schemes = schemes
            )
        }
    }
}

@Composable
fun SelectYearDropdownList(
    modifier: Modifier = Modifier,
    isExpanded: MutableState<Boolean>,
    onYearSelected: (Int) -> Unit,
    thisYear: Int,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val density = LocalDensity.current
    val screenHeightDp = getScreenHeightDp(ctx, density)
    val itemHeightDp = (screenHeightDp - getInsetsVerticalPaddingDp() - 32) / 18
    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        },
        modifier = modifier.background(schemes.color.dropdownBackground),
        offset = DpOffset(x = (-5).dp, y = (-itemHeightDp - 5).dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .height((itemHeightDp * 14).dp)
                .width(schemes.size.horizontal.yearDropdown.plus(5.dp)),
            state = LazyListState(getYearIndex(thisYear))
        ) {
            items(201) { yearIndex ->
                val color = if (thisYear == getYear(yearIndex))
                    schemes.color.selectedItemInDropdown
                else
                    schemes.color.notSelectedYearInDropdown
                DropdownMenuItem(
                    text = {
                        Text(
                            text = getYear(yearIndex).toString(),
                            modifier = Modifier.fillMaxWidth(),
                            color = color,
                            fontFamily = if (thisYear == getYear(yearIndex))
                                MONTSERRAT_MEDIUM else MONTSERRAT,
                            fontSize = schemes.size.font.main,
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        isExpanded.value = false
                        onYearSelected(getYear(yearIndex))
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
