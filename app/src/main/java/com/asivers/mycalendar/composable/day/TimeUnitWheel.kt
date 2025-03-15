package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.SchemeContainer

private const val MEDIUM_INDEX: Int = Int.MAX_VALUE / 2

@Composable
fun TimeUnitWheel(
    modifier: Modifier = Modifier,
    onItemSelected: (item: Int) -> Unit,
    numberOfItems: Int,
    initialSelectedItem: Int,
    schemes: SchemeContainer
) {
    val itemHeight = 50.dp
    val itemHeightFloat = with(LocalDensity.current) { itemHeight.toPx() }

    val initialIndexOfZeroItem = (MEDIUM_INDEX / numberOfItems) * numberOfItems
    val initialIndexOfSelectedItem = initialIndexOfZeroItem + initialSelectedItem

    val indexOfSelectedItemState = remember { mutableIntStateOf(initialIndexOfSelectedItem) }
    val scrollState = rememberLazyListState(initialIndexOfSelectedItem - 1)

    LazyColumn(
        modifier = modifier
            .height(itemHeight * numberOfItems)
            .width(70.dp),
        state = scrollState,
        flingBehavior = rememberSnapFlingBehavior(scrollState)
    ) {
        items(
            count = Int.MAX_VALUE,
            itemContent = { index ->
                val item = index % numberOfItems
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            if (it.positionInParent().y == itemHeightFloat) {
                                onItemSelected(item)
                                indexOfSelectedItemState.intValue = index
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    NumberInTimeUnitWheel(
                        item = item,
                        isSelected = indexOfSelectedItemState.intValue == index,
                        schemes = schemes
                    )
                }
            }
        )
    }
}

@Composable
fun NumberInTimeUnitWheel(
    modifier: Modifier = Modifier,
    item: Int,
    isSelected: Boolean,
    schemes: SchemeContainer
) {
    val alpha = if (isSelected) 1f else 0.3f
    val fontSize = if (isSelected)
        schemes.size.font.main else schemes.size.font.dropdownItem
    Text(
        modifier = modifier.alpha(alpha),
        text = item.toString().padStart(2, '0'),
        fontFamily = MONTSERRAT_MEDIUM,
        color = schemes.color.viewsBottom,
        fontSize = fontSize
    )
}
