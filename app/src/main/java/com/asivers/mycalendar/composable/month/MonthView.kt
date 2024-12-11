package com.asivers.mycalendar.composable.month

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.composable.dropdown.TopDropdownsRow
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedMonthInfo
import com.asivers.mycalendar.data.SelectedYearInfo
import com.asivers.mycalendar.data.ViewShownInfo
import com.asivers.mycalendar.enums.ViewShown
import com.asivers.mycalendar.enums.WeekendMode
import com.asivers.mycalendar.utils.PreviewFrameWithSettingsHeader
import com.asivers.mycalendar.utils.getCurrentMonthIndex
import com.asivers.mycalendar.utils.getCurrentYear
import com.asivers.mycalendar.utils.getIndentFromHeaderDp
import com.asivers.mycalendar.utils.getMonthAndYearViewBackgroundGradient
import com.asivers.mycalendar.utils.getSchemesForPreview
import com.asivers.mycalendar.utils.nextMonth
import com.asivers.mycalendar.utils.previousMonth

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun MonthViewPreview() {
    val schemes = getSchemesForPreview(LocalConfiguration.current, LocalDensity.current)
    val viewShownInfo = remember { mutableStateOf(ViewShownInfo(ViewShown.MONTH)) }
    SharedTransitionLayout {
        AnimatedContent(
            targetState = viewShownInfo.value,
            label = "preview day view animated content"
        ) { viewShownInfoValue ->
            PreviewFrameWithSettingsHeader(
                viewShown = viewShownInfoValue.current,
                getBackground = { getMonthAndYearViewBackgroundGradient(it) },
                schemes = schemes
            ) {
                MonthView(
                    selectedYearInfo = remember { mutableStateOf(SelectedYearInfo(getCurrentYear())) },
                    selectedMonthInfo = remember {
                        mutableStateOf(
                            SelectedMonthInfo(
                                getCurrentYear(),
                                getCurrentMonthIndex()
                            )
                        )
                    },
                    viewShownInfo = viewShownInfo,
                    onDaySelected = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedContent,
                    weekendMode = WeekendMode.SATURDAY_SUNDAY,
                    schemes = schemes
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MonthView(
    modifier: Modifier = Modifier,
    selectedYearInfo: MutableState<SelectedYearInfo>,
    selectedMonthInfo: MutableState<SelectedMonthInfo>,
    viewShownInfo: MutableState<ViewShownInfo>,
    onDaySelected: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    weekendMode: WeekendMode,
    schemes: SchemeContainer
) {
    val indentFromHeaderDp = getIndentFromHeaderDp(LocalConfiguration.current.screenHeightDp)
    var horizontalOffset by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier
            .padding(0.dp, indentFromHeaderDp.dp, 0.dp, 0.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        horizontalOffset = 0f
                    },
                    onDragEnd = {
                        if (horizontalOffset > 50f) {
                            previousMonth(selectedYearInfo, selectedMonthInfo)
                        } else if (horizontalOffset < -50f) {
                            nextMonth(selectedYearInfo, selectedMonthInfo)
                        }
                    }
                ) { _, dragAmount ->
                    horizontalOffset += dragAmount
                }
            }
    ) {
        with(sharedTransitionScope) {
            TopDropdownsRow(
                modifier = Modifier
                    .weight(1f)
                    .sharedElement(
                        rememberSharedContentState(key = "topDropdownsRow"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                selectedYearInfo = selectedYearInfo,
                selectedMonthInfo = selectedMonthInfo,
                viewShownInfo = viewShownInfo,
                schemes = schemes
            )
        }
        Column(modifier = Modifier.weight(8f)) {
            MonthCalendarGrid(
                selectedMonthInfo = selectedMonthInfo,
                onDaySelected = onDaySelected,
                weekendMode = weekendMode,
                schemes = schemes
            )
            ClickableSpacers(
                modifier = Modifier.fillMaxSize(),
                onClickLeft = { previousMonth(selectedYearInfo, selectedMonthInfo) },
                onClickRight = { nextMonth(selectedYearInfo, selectedMonthInfo) }
            )
        }
        YearViewButton(
            viewShownInfo = viewShownInfo,
            schemes = schemes
        )
    }
}
