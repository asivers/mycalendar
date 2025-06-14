package com.asivers.mycalendar.data

import org.junit.Assert.assertEquals
import org.junit.Test

class NoteInfoTest {

    @Test
    fun noteInfosSorted_isCorrect() {
        val noteInfos = listOf(
            NoteInfo(1, "1", false, NotificationTime(10, 0)),
            NoteInfo(2, "2", false, null),
            NoteInfo(3, "3", false, NotificationTime(0, 0)),
            NoteInfo(4, "4", false, null),
            NoteInfo(5, "5", false, NotificationTime(0, 30)),
            NoteInfo(6, "6", false, null)
        )

        val correctOrder = listOf(3, 5, 1, 6, 4, 2)

        assertEquals(correctOrder, noteInfos.sorted().map { it.id })
    }

}
