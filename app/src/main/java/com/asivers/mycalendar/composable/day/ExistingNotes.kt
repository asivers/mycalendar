package com.asivers.mycalendar.composable.day

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.asivers.mycalendar.R
import com.asivers.mycalendar.constants.MONTSERRAT_MEDIUM
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SchemeContainer
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.utils.getExistingNoteBackgroundAlpha
import com.asivers.mycalendar.utils.getScreenHeightDp
import com.asivers.mycalendar.utils.multiplyAlpha
import com.asivers.mycalendar.utils.noRippleClickable
import com.asivers.mycalendar.utils.notification.cancelExactAlarmIfExists
import com.asivers.mycalendar.utils.proto.removeNote
import com.asivers.mycalendar.utils.withDragToRight

@Composable
fun ExistingNotes(
    modifier: Modifier = Modifier,
    mutableNotes: SnapshotStateList<NoteInfo>,
    onClickToNote: (NoteInfo) -> Unit,
    refreshDaysLine: () -> Unit,
    selectedDateInfo: SelectedDateInfo,
    schemes: SchemeContainer
) {
    val ctx = LocalContext.current
    val density = LocalDensity.current
    val screenHeightDp = getScreenHeightDp(ctx, density)
    val maxExistingNotesHeightDp = screenHeightDp - 380
    val maxDrag = with(density) { 48.dp.toPx() }
    LazyColumn(
        modifier = modifier
            .heightIn(0.dp, maxExistingNotesHeightDp.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(mutableNotes, key = { it.id }) { noteInfo ->
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                val maxNoteHeight = maxOf(48, maxExistingNotesHeightDp / mutableNotes.size)
                val horizontalOffset = remember { mutableFloatStateOf(0f) }
                OneSavedNote(
                    modifier = Modifier
                        .heightIn(0.dp, maxNoteHeight.dp)
                        .noRippleClickable { onClickToNote(noteInfo) }
                        .withDragToRight(horizontalOffset, maxDrag),
                    noteInfo = noteInfo,
                    schemes = schemes
                )
                if (horizontalOffset.floatValue == maxDrag) {
                    Image(
                        painter = painterResource(id = R.drawable.delete_trash_can),
                        modifier = Modifier
                            .size(32.dp)
                            .noRippleClickable {
                                cancelExactAlarmIfExists(ctx, noteInfo.id)
                                removeNote(ctx, selectedDateInfo, noteInfo.id)
                                mutableNotes.remove(noteInfo)
                                refreshDaysLine()
                            },
                        contentDescription = "Delete",
                        colorFilter = ColorFilter.tint(schemes.color.text)
                    )
                }
            }
        }
    }
}

@Composable
fun OneSavedNote(
    modifier: Modifier = Modifier,
    noteInfo: NoteInfo,
    schemes: SchemeContainer
) {
    val backgroundAlpha = getExistingNoteBackgroundAlpha(schemes.color)
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(schemes.color.existingNoteBackground.multiplyAlpha(backgroundAlpha))
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = noteInfo.msg,
            fontFamily = MONTSERRAT_MEDIUM,
            fontSize = schemes.size.font.dropdownItem,
            color = schemes.color.text,
        )
        if (noteInfo.isEveryYear || noteInfo.notificationTime != null) {
            Spacer(modifier = Modifier.width(12.dp))
        }
        if (noteInfo.isEveryYear) {
            Image(
                painter = painterResource(id = R.drawable.every_year_calendar),
                colorFilter = ColorFilter.tint(schemes.color.text),
                contentDescription = "Every year note icon",
                modifier = Modifier
                    .padding(4.dp, 0.dp, 2.dp, 0.dp)
                    .size(24.dp)
            )
        }
        if (noteInfo.notificationTime != null) {
            Image(
                painter = painterResource(id = R.drawable.notify_bell),
                colorFilter = ColorFilter.tint(schemes.color.text),
                contentDescription = "Note with notification icon",
                modifier = Modifier
                    .padding(4.dp, 2.dp, 0.dp, 0.dp)
                    .size(21.dp)
            )
        }
    }
}
