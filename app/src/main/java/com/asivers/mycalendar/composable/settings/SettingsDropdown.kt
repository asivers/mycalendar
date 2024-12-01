package com.asivers.mycalendar.composable.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.constants.schemes.SUMMER
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.Country
import com.asivers.mycalendar.enums.SettingsEnum
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.getSettingViewBackgroundGradient
import com.asivers.mycalendar.utils.getTranslatedSettingsItemName
import com.asivers.mycalendar.utils.getTranslatedSettingsItemsNames
import com.asivers.mycalendar.utils.getTranslatedSettingsParamName
import com.asivers.mycalendar.utils.noRippleClickable
import kotlin.enums.enumEntries

private const val MAX_ITEMS_DISPLAYED: Int = 3

@Preview(showBackground = true)
@Composable
fun SettingsDropdownPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = getSettingViewBackgroundGradient(SUMMER))
                .padding(innerPadding)
                .padding(10.dp, 10.dp)
        ) {
            SettingsDropdown(
                selectedItem = remember { mutableStateOf(Country.RUSSIA) },
                allItems = enumEntries<Country>(),
                schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
            )
        }
    }
}

@Composable
fun <T : SettingsEnum> SettingsDropdown(
    modifier: Modifier = Modifier,
    selectedItem: MutableState<T>,
    allItems: List<T>,
    schemes: SchemeContainer
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    val translatedParamName = getTranslatedSettingsParamName(allItems, schemes.translation)
    Column(
        modifier = modifier
            .background(Color.Transparent)
            .noRippleClickable { isExpanded.value = true }
    ) {
        Row(
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
                    text = translatedParamName,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                    color = Color.White,
                    fontFamily = MONTSERRAT_BOLD,
                    fontSize = schemes.size.font.dropdownHeader
                )
                if (allItems.size > MAX_ITEMS_DISPLAYED) {
                    SettingsScrollableDropdownList(
                        isExpanded = isExpanded,
                        selectedItem = selectedItem,
                        allItems = allItems,
                        schemes = schemes
                    )
                } else {
                    SettingsDropdownList(
                        isExpanded = isExpanded,
                        selectedItem = selectedItem,
                        allItems = allItems,
                        schemes = schemes
                    )
                }
            }
        }
        Text(
            text = getTranslatedSettingsItemName(selectedItem.value, schemes.translation),
            modifier = Modifier.offset(x = 25.dp, y = 2.dp),
            color = schemes.color.brightElement,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.dropdownHeader
        )
    }
}

@Composable
fun <T : SettingsEnum> SettingsDropdownList(
    modifier: Modifier = Modifier,
    isExpanded: MutableState<Boolean>,
    selectedItem: MutableState<T>,
    allItems: List<T>,
    schemes: SchemeContainer
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val itemHeightDp = (screenHeightDp - 32) / 17
    val translatedItemsNames = getTranslatedSettingsItemsNames(allItems, schemes.translation)
    val selectedItemIndex = allItems.indexOf(selectedItem.value)
    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        },
        modifier = modifier
    ) {
        translatedItemsNames.forEachIndexed { index, translatedItemName ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = translatedItemName,
                        color = if (selectedItemIndex == index)
                            schemes.color.viewsBottom else schemes.color.settingsViewTop,
                        fontFamily = if (selectedItemIndex == index)
                            MONTSERRAT_BOLD else MONTSERRAT_MEDIUM,
                        fontSize = schemes.size.font.main
                    )
                },
                onClick = {
                    isExpanded.value = false
                    selectedItem.value = allItems[index]
                },
                modifier = Modifier.height(itemHeightDp.dp)
            )
        }
    }
}

@Composable
fun <T : SettingsEnum> SettingsScrollableDropdownList(
    modifier: Modifier = Modifier,
    isExpanded: MutableState<Boolean>,
    selectedItem: MutableState<T>,
    allItems: List<T>,
    schemes: SchemeContainer
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val itemHeightDp = (screenHeightDp - 32) / 17 // todo change
    val translatedItemsNames = getTranslatedSettingsItemsNames(allItems, schemes.translation)
    val selectedItemIndex = allItems.indexOf(selectedItem.value)
    DropdownMenu(
        expanded = isExpanded.value,
        onDismissRequest = {
            isExpanded.value = false
        },
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .height((MAX_ITEMS_DISPLAYED * itemHeightDp).dp)
                .width(205.dp), // todo adapt for different size schemes
            state = LazyListState(selectedItemIndex)
        ) {
            items(translatedItemsNames.size) { index ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = translatedItemsNames[index],
                            color = if (selectedItemIndex == index)
                                schemes.color.viewsBottom else schemes.color.settingsViewTop,
                            fontFamily = if (selectedItemIndex == index)
                                MONTSERRAT_BOLD else MONTSERRAT_MEDIUM,
                            fontSize = schemes.size.font.main
                        )
                    },
                    onClick = {
                        isExpanded.value = false
                        selectedItem.value = allItems[index]
                    },
                    modifier = Modifier.height(itemHeightDp.dp)
                )
            }
        }
    }
}
