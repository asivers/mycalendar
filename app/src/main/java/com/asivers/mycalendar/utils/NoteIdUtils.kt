package com.asivers.mycalendar.utils

import android.content.Context
import com.asivers.mycalendar.utils.proto.getMaxNoteId

private var maxNoteId: Int? = null

fun getNextNoteId(ctx: Context): Int {
    val nextNoteId = ((maxNoteId ?: getMaxNoteId(ctx)) ?: 0) + 1
    maxNoteId = nextNoteId
    return nextNoteId
}
