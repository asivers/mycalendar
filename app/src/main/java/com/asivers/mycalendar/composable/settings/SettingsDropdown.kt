package com.asivers.mycalendar.composable.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTSERRAT_BOLD
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.enums.SettingsItem
import com.asivers.mycalendar.enums.SettingsParam
import com.asivers.mycalendar.utils.getInsetsVerticalPaddingDp
import com.asivers.mycalendar.utils.getScreenHeightDp
import com.asivers.mycalendar.utils.getTranslatedSettingsItemName
import com.asivers.mycalendar.utils.getTranslatedSettingsItemsNames
import com.asivers.mycalendar.utils.getTranslatedSettingsParamName
import com.asivers.mycalendar.utils.noRippleClickable
import com.asivers.mycalendar.utils.proto.updateOneSetting

@Composable
fun <T : SettingsItem> SettingsDropdown(
    modifier: Modifier = Modifier,
    expanded: MutableState<SettingsParam?>,
    selectedItem: MutableState<T>,
    settingsParam: SettingsParam,
    allItems: List<T>,
    maxItemsDisplayed: Int,
    schemes: SchemeContainer
) {
    val translatedParamName = getTranslatedSettingsParamName(settingsParam, schemes.translation)
    val enabled = expanded.value == null || expanded.value == settingsParam
    Column(
        modifier = modifier
            .background(Color.Transparent)
            .noRippleClickable { expanded.value = settingsParam }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconId = if (expanded.value == settingsParam)
                R.drawable.white_arrow_up else R.drawable.white_arrow_down
            Image(
                painter = painterResource(id = iconId),
                colorFilter = ColorFilter.tint(
                    if (enabled) schemes.color.text else Color.Transparent
                ),
                contentDescription = "DropDown Icon"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box {
                Text(
                    text = translatedParamName,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 3.dp)
                        .alpha(if (enabled) 1f else 0.5f),
                    color = schemes.color.text,
                    fontFamily = MONTSERRAT_BOLD,
                    fontSize = schemes.size.font.dropdownItem
                )
                if (allItems.size > maxItemsDisplayed) {
                    SettingsScrollableDropdownList(
                        expanded = expanded,
                        selectedItem = selectedItem,
                        settingsParam = settingsParam,
                        allItems = allItems,
                        maxItemsDisplayed = maxItemsDisplayed,
                        schemes = schemes
                    )
                } else {
                    SettingsDropdownList(
                        expanded = expanded,
                        selectedItem = selectedItem,
                        settingsParam = settingsParam,
                        allItems = allItems,
                        schemes = schemes
                    )
                }
            }
        }
        Text(
            text = getTranslatedSettingsItemName(selectedItem.value, schemes.translation),
            modifier = Modifier
                .offset(x = 25.dp, y = 2.dp)
                .alpha(if (enabled) 1f else 0.5f),
            color = schemes.color.brightElement,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.dropdownItem
        )
    }
}

@Composable
fun <T : SettingsItem> SettingsDropdownList(
    modifier: Modifier = Modifier,
    expanded: MutableState<SettingsParam?>,
    selectedItem: MutableState<T>,
    settingsParam: SettingsParam,
    allItems: List<T>,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val density = LocalDensity.current
    val screenHeightDp = getScreenHeightDp(ctx, density)
    val itemHeightDp = (screenHeightDp - getInsetsVerticalPaddingDp() - 32) / 17
    val translatedItemsNames = getTranslatedSettingsItemsNames(allItems, schemes.translation)
    val selectedItemIndex = allItems.indexOf(selectedItem.value)
    DropdownMenu(
        expanded = expanded.value == settingsParam,
        onDismissRequest = { expanded.value = null },
        modifier = modifier.background(schemes.color.dropdownBackground),
        offset = DpOffset(x = 0.dp, y = 0.dp)
    ) {
        translatedItemsNames.forEachIndexed { index, translatedItemName ->
            val color = if (selectedItemIndex == index)
                schemes.color.selectedItemInDropdown
            else
                schemes.color.notSelectedSettingInDropdown
            DropdownMenuItem(
                text = {
                    Text(
                        text = translatedItemName,
                        color = color,
                        fontFamily = if (selectedItemIndex == index)
                            MONTSERRAT_BOLD else MONTSERRAT_MEDIUM,
                        fontSize = schemes.size.font.dropdownItem
                    )
                },
                onClick = {
                    expanded.value = null
                    val newSelectedItem = allItems[index]
                    selectedItem.value = newSelectedItem
                    updateOneSetting(settingsParam, newSelectedItem, ctx)
                },
                modifier = Modifier.height(itemHeightDp.dp)
            )
        }
    }
}

@Composable
fun <T : SettingsItem> SettingsScrollableDropdownList(
    modifier: Modifier = Modifier,
    expanded: MutableState<SettingsParam?>,
    selectedItem: MutableState<T>,
    settingsParam: SettingsParam,
    allItems: List<T>,
    maxItemsDisplayed: Int,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val density = LocalDensity.current
    val screenHeightDp = getScreenHeightDp(ctx, density)
    val itemHeightDp = (screenHeightDp - getInsetsVerticalPaddingDp() - 32) / 17
    val translatedItemsNames = getTranslatedSettingsItemsNames(allItems, schemes.translation)
    val selectedItemIndex = allItems.indexOf(selectedItem.value)
    DropdownMenu(
        expanded = expanded.value == settingsParam,
        onDismissRequest = { expanded.value = null },
        modifier = modifier.background(schemes.color.dropdownBackground),
        offset = DpOffset(x = 0.dp, y = 0.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .height(((maxItemsDisplayed - 0.5) * itemHeightDp).dp)
                .width(205.dp),
            state = LazyListState(selectedItemIndex)
        ) {
            items(translatedItemsNames.size) { index ->
                val color = if (selectedItemIndex == index)
                    schemes.color.selectedItemInDropdown
                else
                    schemes.color.notSelectedSettingInDropdown
                DropdownMenuItem(
                    text = {
                        Text(
                            text = translatedItemsNames[index],
                            color = color,
                            fontFamily = if (selectedItemIndex == index)
                                MONTSERRAT_BOLD else MONTSERRAT_MEDIUM,
                            fontSize = schemes.size.font.dropdownItem
                        )
                    },
                    onClick = {
                        expanded.value = null
                        val newSelectedItem = allItems[index]
                        selectedItem.value = newSelectedItem
                        updateOneSetting(settingsParam, newSelectedItem, ctx)
                    },
                    modifier = Modifier.height(itemHeightDp.dp)
                )
            }
        }
    }
}
