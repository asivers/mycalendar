package com.asivers.mycalendar.utils.proto

import android.content.Context
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.NoteInfoWithDate
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.data.proto.SavedNotesOuterClass.SavedNotes.Note
import com.asivers.mycalendar.serializers.savedNotesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

fun getNotes(
    ctx: Context,
    selectedDateInfo: SelectedDateInfo
): List<NoteInfo> {
    val allNotesForMonthAndDay = runBlocking { ctx.savedNotesDataStore.data.first() }
        .forMonthList
        .find { it.monthValue == selectedDateInfo.monthValue }
        ?.forDayList
        ?.find { it.dayOfMonth == selectedDateInfo.dayOfMonth }
        ?.notesList
        ?: listOf<Note>()
    return allNotesForMonthAndDay
        .filter { it.forYear == 0 || it.forYear == selectedDateInfo.year }
        .map { NoteInfo(it) }
        .sorted()
}

fun getMaxNoteId(ctx: Context): Int? {
    return runBlocking { ctx.savedNotesDataStore.data.first() }
        .forMonthList
        .flatMap { it.forDayList }
        .flatMap { it.notesList }
        .maxOfOrNull { it.id }
}

fun getDaysWithNotesForMonth(
    ctx: Context,
    year: Int,
    monthValue: Int
): List<Int> {
    return runBlocking { ctx.savedNotesDataStore.data.first() }
        .forMonthList
        .find { it.monthValue == monthValue }
        ?.forDayList
        ?.filter { it.notesList != null && containsNotesForYear(it.notesList, year) }
        ?.map { it.dayOfMonth }
        ?.sorted()
        ?: emptyList()
}

private fun containsNotesForYear(notesList: List<Note>, year: Int): Boolean {
    return notesList.map { it.forYear }.any { it == 0 || it == year }
}

fun areNotificationsExist(ctx: Context): Boolean {
    return runBlocking { ctx.savedNotesDataStore.data.first() }
        .forMonthList
        .flatMap { it.forDayList }
        .flatMap { it.notesList }
        .find { !it.notificationTimeNull } != null
}

fun getInfoAboutAllNotifications(ctx: Context): List<NoteInfoWithDate> {
    val noteInfosWithDates = mutableListOf<NoteInfoWithDate>()
    runBlocking { ctx.savedNotesDataStore.data.first() }.forMonthList.forEach { forMonth ->
        val monthValue = forMonth.monthValue
        forMonth.forDayList.forEach { forDay ->
            val dayOfMonth = forDay.dayOfMonth
            forDay.notesList.filter { !it.notificationTimeNull }.forEach { note ->
                val noteInfoWithDate = NoteInfoWithDate(
                    noteInfo = NoteInfo(note),
                    year = if (note.forYear == 0) null else note.forYear,
                    monthValue = monthValue,
                    dayOfMonth = dayOfMonth
                )
                noteInfosWithDates.add(noteInfoWithDate)
            }
        }
    }
    return noteInfosWithDates.toList()
}

fun getInfoAboutAllNotificationsInPast(ctx: Context): List<NoteInfoWithDate> {
    val now = LocalDateTime.now()
    val noteInfosWithDates = mutableListOf<NoteInfoWithDate>()
    runBlocking { ctx.savedNotesDataStore.data.first() }.forMonthList.forEach { forMonth ->
        val monthValue = forMonth.monthValue
        forMonth.forDayList.forEach { forDay ->
            val dayOfMonth = forDay.dayOfMonth
            forDay.notesList
                .filter { !it.notificationTimeNull }
                .filter { it.forYear != 0 }
                .forEach { note ->
                    val notificationLocalDateTime = LocalDateTime.of(
                        note.forYear,
                        monthValue,
                        dayOfMonth,
                        note.notificationTimeValue.hour,
                        note.notificationTimeValue.minute
                    )
                    if (notificationLocalDateTime.isBefore(now)) {
                        val noteInfoWithDate = NoteInfoWithDate(
                            noteInfo = NoteInfo(note),
                            year = note.forYear,
                            monthValue = monthValue,
                            dayOfMonth = dayOfMonth
                        )
                        noteInfosWithDates.add(noteInfoWithDate)
                    }
                }
        }
    }
    return noteInfosWithDates.toList()
}
