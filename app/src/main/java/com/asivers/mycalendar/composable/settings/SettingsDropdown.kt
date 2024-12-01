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
import com.asivers.mycalendar.enums.SettingsItem
import com.asivers.mycalendar.enums.SettingsParam
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.getSettingViewBackgroundGradient
import com.asivers.mycalendar.utils.getTranslatedSettingsItemName
import com.asivers.mycalendar.utils.getTranslatedSettingsItemsNames
import com.asivers.mycalendar.utils.getTranslatedSettingsParamName
import com.asivers.mycalendar.utils.noRippleClickable
import kotlin.enums.enumEntries

private const val MAX_ITEMS_DISPLAYED: Int = 14

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
                settingsParam = SettingsParam.COUNTRY,
                allItems = enumEntries<Country>(),
                selectedItem = remember { mutableStateOf(Country.RUSSIA) },
                expanded = remember { mutableStateOf(null) },
                schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
            )
        }
    }
}

@Composable
fun <T : SettingsItem> SettingsDropdown(
    modifier: Modifier = Modifier,
    settingsParam: SettingsParam,
    allItems: List<T>,
    selectedItem: MutableState<T>,
    expanded: MutableState<SettingsParam?>,
    schemes: SchemeContainer
) {
    val translatedParamName = getTranslatedSettingsParamName(settingsParam, schemes.translation)
    Column(
        modifier = modifier
            .background(Color.Transparent)
            .noRippleClickable { expanded.value = settingsParam }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconId = if (expanded.value != null)
                R.drawable.white_arrow_up else R.drawable.white_arrow_down
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
                        allItems = allItems,
                        selectedItem = selectedItem,
                        expanded = expanded,
                        schemes = schemes
                    )
                } else {
                    SettingsDropdownList(
                        allItems = allItems,
                        selectedItem = selectedItem,
                        expanded = expanded,
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
            fontSize = schemes.size.font.main
        )
    }
}

@Composable
fun <T : SettingsItem> SettingsDropdownList(
    modifier: Modifier = Modifier,
    allItems: List<T>,
    selectedItem: MutableState<T>,
    expanded: MutableState<SettingsParam?>,
    schemes: SchemeContainer
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val itemHeightDp = (screenHeightDp - 32) / 17
    val translatedItemsNames = getTranslatedSettingsItemsNames(allItems, schemes.translation)
    val selectedItemIndex = allItems.indexOf(selectedItem.value)
    DropdownMenu(
        expanded = expanded.value != null,
        onDismissRequest = { expanded.value = null },
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
                    expanded.value = null
                    selectedItem.value = allItems[index]
                },
                modifier = Modifier.height(itemHeightDp.dp)
            )
        }
    }
}

@Composable
fun <T : SettingsItem> SettingsScrollableDropdownList(
    modifier: Modifier = Modifier,
    allItems: List<T>,
    selectedItem: MutableState<T>,
    expanded: MutableState<SettingsParam?>,
    schemes: SchemeContainer
) {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val itemHeightDp = (screenHeightDp - 32) / 17 // todo change
    val translatedItemsNames = getTranslatedSettingsItemsNames(allItems, schemes.translation)
    val selectedItemIndex = allItems.indexOf(selectedItem.value)
    DropdownMenu(
        expanded = expanded.value != null,
        onDismissRequest = { expanded.value = null },
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
                        expanded.value = null
                        selectedItem.value = allItems[index]
                    },
                    modifier = Modifier.height(itemHeightDp.dp)
                )
            }
        }
    }
}
